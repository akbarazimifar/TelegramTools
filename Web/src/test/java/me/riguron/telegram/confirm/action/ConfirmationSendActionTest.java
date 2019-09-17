package me.riguron.telegram.confirm.action;

import me.riguron.telegram.confirm.ConfirmationService;
import me.riguron.telegram.user.UserSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConfirmationSendActionTest {

    private ConfirmationSendAction confirmationSendAction;

    private ConfirmationService confirmationService;

    @Before
    public void execute() {
        this.confirmationService = mock(ConfirmationService.class);
        UserSession userSession = mock(UserSession.class);

        this.confirmationSendAction = new ConfirmationSendAction(
                confirmationService,
                userSession
        );
    }

    @Test
    public void whenCodeSentThenAddedInfo() {
        executeAndExpect(true, "info");
    }

    @Test
    public void whenCodeNotSentThenAddedError() {
        executeAndExpect(false, "error");
    }

    private void executeAndExpect(boolean send, String expectedAttribute) {
        when(this.confirmationService.sendCode(any())).thenReturn(send);
        Model model = mock(Model.class);
        confirmationSendAction.execute(model, Collections.emptyMap());
        verify(model).addAttribute(eq(expectedAttribute), anyString());
    }
}