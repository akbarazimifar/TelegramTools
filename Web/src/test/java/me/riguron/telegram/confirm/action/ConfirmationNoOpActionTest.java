package me.riguron.telegram.confirm.action;

import org.junit.Test;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConfirmationNoOpActionTest {

    @Test
    public void execute() {
        ConfirmationNoOpAction confirmationNoOpAction = ConfirmationNoOpAction.INSTANCE;
        Model model = mock(Model.class);
        confirmationNoOpAction.execute(model, Collections.emptyMap());
        verify(model).addAttribute(eq("error"), any());
    }
}