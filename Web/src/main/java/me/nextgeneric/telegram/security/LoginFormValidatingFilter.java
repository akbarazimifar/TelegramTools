package me.nextgeneric.telegram.security;

import me.nextgeneric.telegram.validate.LoginForm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class LoginFormValidatingFilter extends UsernamePasswordAuthenticationFilter {

    private Validator validator;

    public LoginFormValidatingFilter(Validator validator) {
        this.validator = validator;
        this.setUsernameParameter("login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        LoginForm loginForm = new LoginForm(username, password);

        Set<ConstraintViolation<LoginForm>> validationResult = validator.validate(loginForm);



        if (!validationResult.isEmpty()) {
            ConstraintViolation<LoginForm> violation = validationResult.iterator().next();
            throw new LoginFormValidationException(violation.getMessage());
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    class LoginFormValidationException extends AuthenticationException {

        private LoginFormValidationException(String validationError) {
            super(validationError);
        }
    }


}
