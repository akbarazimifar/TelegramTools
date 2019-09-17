package me.nextgeneric.telegram.application;

import me.nextgeneric.telegram.DocumentType;
import me.nextgeneric.telegram.SystemRoles;
import me.nextgeneric.telegram.date.DateFormat;
import me.nextgeneric.telegram.dto.ActiveDumpTaskView;
import me.nextgeneric.telegram.dto.HistoryDumpTaskView;
import me.nextgeneric.telegram.dump.ChannelDumpService;
import me.nextgeneric.telegram.dump.ChannelDumpTask;
import me.nextgeneric.telegram.dump.DumpOptions;
import me.nextgeneric.telegram.dump.execution.ChannelDumpExecutionManager;
import me.nextgeneric.telegram.dump.state.ChannelDumpState;
import me.nextgeneric.telegram.projection.TaskProjection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.Collections;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ApplicationController.class)
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChannelDumpService channelDumpService;

    @MockBean
    private ChannelDumpExecutionManager executionManager;


    @Autowired
    private DateFormat dateFormat;


    @Autowired
    private MessageSource messageSource;

    @Test
    @WithMockUser(roles = SystemRoles.FULL)
    public void viewLoads() throws Exception {

        long now = System.currentTimeMillis();

        TaskProjection taskProjection = new TaskProjection(System.currentTimeMillis(), "channel", true, DocumentType.PDF, "file", "execution.state.completed",
                true);

        ChannelDumpTask channelDumpTask = mock(ChannelDumpTask.class);

        when(channelDumpTask.getDateStarted()).thenReturn(now);
        when(channelDumpTask.getOptions()).thenReturn(new DumpOptions(
                "channel", DocumentType.PDF, true, new File("target"), 0)
        );
        when(channelDumpTask.getState()).thenAnswer(invocationOnMock -> {
            ChannelDumpState state = mock(ChannelDumpState.class);
            when(state.publicDescription()).thenReturn("execution.state.messages_loading");
            when(state.getStateVariables()).thenReturn(Collections.emptyList());
            return state;
        });


        when(channelDumpService.getHistory(anyInt())).thenReturn(Collections.singletonList(taskProjection));
        when(executionManager.getActiveTasks(anyInt())).thenReturn(Collections.singletonList(channelDumpTask));

        this.mockMvc.perform(
                get("/application")
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("application")
        ).andExpect(
                model().attribute("language", "en.png")
        ).andExpect(
                model().attribute("history", Collections.singletonList(
                        new HistoryDumpTaskView(
                                dateFormat.format(now, Locale.ENGLISH),
                                "channel",
                                true,
                                DocumentType.PDF,
                                "file",
                                messageSource.getMessage("execution.state.completed", new Object[0], Locale.ENGLISH),
                                true

                        )
                ))
        ).andExpect(
                model().attribute("tasks", Collections.singletonList(new ActiveDumpTaskView(
                        dateFormat.format(now, Locale.ENGLISH),
                        "channel",
                        "pdf",
                        "Loading messages: {0}"
                )))
        );

        verify(executionManager).getActiveTasks(anyInt());
        verify(channelDumpService).getHistory(anyInt());
    }

    @Test
    @WithMockUser
    public void whenAttemptToLoadWithoutRoleThenRedirectedToConfirm() throws Exception {

        this.mockMvc.perform(
                get("/application")
        ).andExpect(
                status().is3xxRedirection()
        ).andExpect(
                redirectedUrl("/confirm")
        );
    }

    @Test
    public void whenLoginUnauthorizedThenSentToLogin() throws Exception {
        this.mockMvc.perform(
                get("/application")
        ).andExpect(
                status().is3xxRedirection()
        ).andExpect(
               redirectedUrlPattern("**/login")
        );
    }


}