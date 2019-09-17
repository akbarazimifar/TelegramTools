package me.riguron.telegram.confirm;

import me.riguron.telegram.SystemRoles;
import me.riguron.telegram.security.RoleAssistant;
import me.riguron.telegram.SystemRoles;
import me.riguron.telegram.security.RoleAssistant;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConfirmationFilter extends OncePerRequestFilter {

    private static final String CONFIRM = "/confirm";

    private RoleAssistant roleAssistant;

    public ConfirmationFilter(RoleAssistant roleAssistant) {
        this.roleAssistant = roleAssistant;
    }

    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() != null && needsConfirmation(httpServletRequest)) {
            httpServletResponse.sendRedirect(CONFIRM);
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private boolean needsConfirmation(HttpServletRequest servletRequest) {
        return !(roleAssistant.isAnonymous() || CONFIRM.equals(servletRequest.getRequestURI()) || roleAssistant.hasRole(SystemRoles.ROLE_FULL));
    }

}
