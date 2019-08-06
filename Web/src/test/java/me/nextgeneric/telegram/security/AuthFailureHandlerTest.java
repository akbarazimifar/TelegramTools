package me.nextgeneric.telegram.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.FlashMapManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AuthFailureHandlerTest {

    private AuthFailureHandler authFailureHandler;

    private FlashMapManager flashMapManager;

    @Before
    public void setUp() {
        this.flashMapManager = mock(FlashMapManager.class);
        this.authFailureHandler = new AuthFailureHandler(flashMapManager);
    }

    @Test
    public void whenExceptionNotNullThenFlashAdded() throws IOException {
        raiseFailure(mock(AuthenticationException.class), true);
    }

    @Test
    public void whenNoExceptionThenJustRedirected() throws IOException {
        raiseFailure(null, false);
    }

    private void raiseFailure(AuthenticationException exception, boolean populateFlashMap) throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        authFailureHandler.onAuthenticationFailure(request, response, exception);

        verify(flashMapManager, times(populateFlashMap ? 1 : 0)).saveOutputFlashMap(any(), eq(request), eq(response));
        verify(response).sendRedirect(eq("/login"));
    }
}