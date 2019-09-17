package me.riguron.telegram.security;

import me.riguron.telegram.SystemRoles;
import me.riguron.telegram.SystemRoles;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
public class RoleAssistant {

    public void set(String... roles) {
        Authentication auth = getContext().getAuthentication();
        getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        auth.getPrincipal(), auth.getCredentials(),
                        Arrays.stream(roles)
                                .map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                )
        );
    }

    public boolean hasRole(String role) {

        if (getContext().getAuthentication().getAuthorities().isEmpty()) {
            return false;
        }

        for (GrantedAuthority grantedAuthority : getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals(role)) {
                return true;
            }
        }

        return false;
    }

    public boolean isAnonymous() {
        return SystemRoles.ANONYMOUS_USER.equals(getContext().getAuthentication().getName());
    }


}
