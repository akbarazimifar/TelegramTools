package me.riguron.telegram.security;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Collections;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class LoginFormValidatingFilterTest {

    private Validator validator;

    private LoginFormValidatingFilter filter;

    private AuthenticationManager authenticationManager;

    private Authentication authentication;

    @Before
    public void doPrepare() {
        this.validator = mock(Validator.class);
        this.filter = Mockito.spy(new LoginFormValidatingFilter(validator));
        this.authenticationManager = mock(AuthenticationManager.class);
        filter.setAuthenticationManager(authenticationManager);
        this.authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(this.authentication);
    }

    @Test
    public void whenAuthValid() {
        HttpServletRequest request = httpServletRequest();
        Authentication authentication = filter.attemptAuthentication(request, mock(HttpServletResponse.class));
        assertSame(this.authentication, authentication);
        verify(authenticationManager).authenticate(any());
    }

    @Test(expected = LoginFormValidatingFilter.LoginFormValidationException.class)
    @SuppressWarnings("unchecked")
    public void whenAuthFails() {
        when(validator.validate(any())).thenReturn(
                Collections.singleton(
                        mock(ConstraintViolation.class)
                )
        );
        HttpServletRequest request = httpServletRequest();
        filter.attemptAuthentication(request, mock(HttpServletResponse.class));
    }

    private HttpServletRequest httpServletRequest() {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getParameter(eq("username"))).thenReturn("user");
        when(httpServletRequest.getParameter(eq("1234"))).thenReturn("password");
        return httpServletRequest;
    }

}