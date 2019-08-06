package me.nextgeneric.telegram.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.nextgeneric.telegram.SystemRoles;
import me.nextgeneric.telegram.channel.ChannelParser;
import me.nextgeneric.telegram.dto.DumpRequestDto;
import me.nextgeneric.telegram.dump.ChannelDumpService;
import me.nextgeneric.telegram.entity.UserProfile;
import me.nextgeneric.telegram.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.FlashAttributeResultMatchers;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ApplicationTaskController.class)
public class ApplicationTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChannelDumpService channelDumpService;

    @MockBean
    private UserService userService;

    @MockBean
    private ChannelParser channelParser;

    @Before
    public void setUp() {
        when(userService.findByUsername(any())).thenReturn(Optional.of(
                mock(UserProfile.class)
        ));
        when(channelParser.parse(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @Test
    @WithMockUser(roles = SystemRoles.FULL)
    public void whenValidDtoThenTaskCreated() throws Exception {

        when(channelDumpService.dump(any(), any(), any())).thenReturn(true);

        DumpRequestDto dumpRequestDto = new DumpRequestDto("channel",
                "pdf", true, true);


        postAndAssertFlash(dumpRequestDto, flashAttributeResultMatchers -> flashAttributeResultMatchers.attributeCount(0), this.bothCalled());
    }


    @Test
    @WithMockUser(roles = SystemRoles.FULL)
    public void whenInvalidDtoThenTaskRejected() throws Exception {

        DumpRequestDto dumpRequestDto = new DumpRequestDto("",
                "pdf", true, true);

        postAndAssertFlash(dumpRequestDto, flashAttributeResultMatchers -> flashAttributeResultMatchers.attributeExists("error"), noneCalled());
    }

    @Test
    @WithMockUser(roles = SystemRoles.FULL)
    public void whenDumpServiceRejectedThenFlashAttributeAdded() throws Exception {

        when(channelDumpService.dump(any(), any(), any())).thenReturn(false);

        DumpRequestDto dumpRequestDto = new DumpRequestDto("channel",
                "pdf", true, true);

        postAndAssertFlash(dumpRequestDto, flashAttributeResultMatchers -> flashAttributeResultMatchers.attributeExists("error"), bothCalled());

    }

    @Test
    @WithMockUser
    public void whenAttemptToLoadWithoutRoleThenRedirectedToConfirm() throws Exception {

        this.mockMvc.perform(
                post("/application")
        ).andExpect(
                status().is3xxRedirection()
        ).andExpect(
                redirectedUrl("/confirm")
        );
    }

    @Test
    public void whenLoginUnauthorizedThenSentToLogin() throws Exception {
        this.mockMvc.perform(
                post("/application")
        ).andExpect(
                status().is3xxRedirection()
        ).andExpect(
                redirectedUrlPattern("**/login")
        );
    }


    private void postAndAssertFlash(DumpRequestDto dumpRequestDto, Function<FlashAttributeResultMatchers, ResultMatcher> flashAssertions, Runnable postAssertions) throws Exception {

        this.mockMvc.perform(
                constructPost(dumpRequestDto)
        ).andExpect(
                redirectedUrl("application")
        ).andExpect(
                flashAssertions.apply(flash())
        ).andExpect(
                status().is3xxRedirection()
        );

        postAssertions.run();
    }

    private Runnable bothCalled() {
        return () -> {
            verify(channelDumpService).dump(any(), any(), any());
            verify(userService).findByUsername(any());
        };
    }

    private Runnable noneCalled() {
        return () -> {
        };
    }

    @SuppressWarnings("unchecked")
    private MockHttpServletRequestBuilder constructPost(DumpRequestDto dumpRequestDto) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(dumpRequestDto);
        Map<String, Object> resultMap = objectMapper.readValue(jsonString, Map.class);
        MockHttpServletRequestBuilder post = post("/application");
        resultMap.forEach((k, v) -> post.param(k, v.toString()));
        return post;

    }
}