package me.nextgeneric.telegram.confirm.action;

import me.nextgeneric.telegram.SystemRoles;
import me.nextgeneric.telegram.confirm.ConfirmationService;
import me.nextgeneric.telegram.security.RoleAssistant;
import me.nextgeneric.telegram.user.UserSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ConfirmationConfirmActionTest {

    private ConfirmationConfirmAction action;

    private UserSession userSession;

    private RoleAssistant roleAssistant;

    private ConfirmationService confirmationService;

    @Before
    public void setUp() {
        this.roleAssistant = mock(RoleAssistant.class);
        this.userSession = mock(UserSession.class);
        this.confirmationService = mock(ConfirmationService.class);
        this.action = new ConfirmationConfirmAction(roleAssistant, userSession, confirmationService);
    }

    @Test
    public void whenAllParamsValidThenReturnApplication() {
        assertSuccess("1234");
        assertSuccess("2345");
        assertSuccess("1");
        assertSuccess("0000");
    }

    @Test
    public void whenInvalidCodeThenFailed() {
        assertFailure("abc123");
        assertFailure("++/");
        assertFailure("");
        assertFailure(null);
    }

    @Test
    public void whenConfirmationServiceFails() {
        when(confirmationService.confirm(eq(userSession), anyInt())).thenReturn(-1);
        Model model = mock(Model.class);
        action.execute(model, code("1234"));
        verify(confirmationService).confirm(any(), anyInt());
        reset(confirmationService);
        verifyAll(0);
        verify(model).addAttribute(eq("error"), anyString());
    }

    private void assertFailure(String code) {
        Model model = mock(Model.class);
        action.execute(model, code(code));
        verifyAll(0);
        verify(model).addAttribute(eq("error"), anyString());
    }

    private void verifyAll(int times) {
        verify(userSession, times(times)).setUserId(eq(1));
        verify(roleAssistant, times(times)).set(SystemRoles.ROLE_FULL);
        verify(confirmationService, times(times)).confirm(any(), anyInt());
    }

    private void assertSuccess(String code) {
        when(confirmationService.confirm(eq(userSession), anyInt())).thenReturn(1);
        Model model = mock(Model.class);
        action.execute(model, code(code));
        verifyAll(1);
        reset(userSession, roleAssistant, confirmationService);
    }

    private Map<String, String> code(String value) {
        Map<String, String> map = new HashMap<>();
        map.put("code", value);
        return map;
    }


}