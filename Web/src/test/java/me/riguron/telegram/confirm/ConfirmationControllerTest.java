package me.riguron.telegram.confirm;

import me.riguron.telegram.SystemRoles;
import me.riguron.telegram.confirm.action.ConfirmationConfirmAction;
import me.riguron.telegram.confirm.action.ConfirmationSendAction;
import me.riguron.telegram.SystemRoles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ConfirmationController.class)
public class ConfirmationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfirmationSendAction sendAction;

    @MockBean
    private ConfirmationConfirmAction confirmationConfirmAction;


    @Test
    @WithMockUser(roles = SystemRoles.UNCONFIRMED)
    public void whenLoadViewThenLoaded() throws Exception {
        this.mockMvc.perform(
                get("/confirm")
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("confirm")
        );
    }

    @Test
    @WithMockUser(roles = SystemRoles.UNCONFIRMED)
    public void whenExecuteValidActionThenExecuted() throws Exception {

        when(sendAction.execute(any(), any())).thenReturn("application");

        this.mockMvc.perform(
                post("/confirm").param("action", "send")
        ).andExpect(
                status().is3xxRedirection()
        ).andExpect(
                redirectedUrl("/application")
        );
    }

    @Test
    @WithMockUser(roles = SystemRoles.UNCONFIRMED)
    public void whenInvalidActionThenRedirectedToConfirm() throws Exception {

        this.mockMvc.perform(
                post("/confirm").param("action", "unknown")
        ).andExpect(
                status().isOk()
        ).andExpect(
                view().name("confirm")
        );
    }

    @Test
    @WithMockUser(roles = SystemRoles.FULL)
    public void whenUserWithFullRoleThenNotPassed() throws Exception {

        this.mockMvc.perform(
                get("/confirm")
        ).andExpect(
                status().isForbidden()
        );
    }

    @Test
    public void whenCompletelyUnauthorizedThenNotPassed() throws Exception {

        this.mockMvc.perform(
                get("/confirm")
        ).andExpect(
                redirectedUrlPattern("**/login")
        );

    }


}