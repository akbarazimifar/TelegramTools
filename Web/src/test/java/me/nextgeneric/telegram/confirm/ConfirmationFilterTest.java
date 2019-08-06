package me.nextgeneric.telegram.confirm;

import me.nextgeneric.telegram.SystemRoles;
import me.nextgeneric.telegram.security.RoleAssistant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest(SecurityContextHolder.class)
public class ConfirmationFilterTest {

    private ConfirmationFilter confirmationFilter;
    private RoleAssistant roleAssistant;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @Before
    public void doPrepare() {
        this.roleAssistant = mock(RoleAssistant.class);
        this.confirmationFilter = new ConfirmationFilter(roleAssistant);
        this.request = mock(HttpServletRequest.class);
        this.response = mock(HttpServletResponse.class);
        this.filterChain = mock(FilterChain.class);
    }

    @Test
    public void whenAllRedirectConditionsAreMetThenRedirected() throws ServletException, IOException {
        mockAuthentication();
        when(roleAssistant.isAnonymous()).thenReturn(false);
        when(request.getRequestURI()).thenReturn("application");
        when(roleAssistant.hasRole(eq(SystemRoles.ROLE_FULL))).thenReturn(false);
        filter();
        assertProceeded(false);
        verify(response).sendRedirect("/confirm");

    }

    @Test
    public void whenAuthenticationIsNullThenPassed() throws ServletException, IOException {
        filter();
        assertProceeded();
    }

    @Test
    public void whenRoleAssistantIsAnonymousThenPassed() throws ServletException, IOException {
        requireConfirmation(() -> when(roleAssistant.isAnonymous()).thenReturn(true));
    }

    @Test
    public void whenRequestingSameUrlThenPassed() throws IOException, ServletException {
        requireConfirmation(() -> when(request.getRequestURI()).thenReturn("/confirm"));
    }

    @Test
    public void whenHasFullRoleThenPassed() throws IOException, ServletException {
        requireConfirmation(() -> when(roleAssistant.hasRole(eq(SystemRoles.ROLE_FULL))).thenReturn(true));
    }

    private void requireConfirmation(Runnable stateConfigurer) throws IOException, ServletException {
        mockAuthentication();
        stateConfigurer.run();
        filter();
        assertProceeded();
    }

    private void filter() throws ServletException, IOException {
        confirmationFilter.doFilterInternal(request, response, filterChain);
    }

    private void assertProceeded() throws IOException, ServletException {
        assertProceeded(true);
    }

    private void assertProceeded(boolean shouldProceed) throws IOException, ServletException {
        verify(filterChain, times(shouldProceed ? 1 : 0)).doFilter(eq(request), eq(response));
    }

    private void mockAuthentication() {
        mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenAnswer(invocationOnMock -> {
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(mock(Authentication.class));
            return securityContext;
        });
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }


}