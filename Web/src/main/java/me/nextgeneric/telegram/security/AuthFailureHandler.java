package me.nextgeneric.telegram.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFailureHandler implements AuthenticationFailureHandler {

    private final FlashMapManager flashMapManager;

    public AuthFailureHandler(FlashMapManager flashMapManager) {
        this.flashMapManager = flashMapManager;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException {
        if (exception != null) {
            FlashMap flashMap = new FlashMap();
            flashMap.put("failureHandlerError", exception.getMessage());
            flashMapManager.saveOutputFlashMap(flashMap, httpServletRequest, httpServletResponse);
        }
        httpServletResponse.sendRedirect("/login");
    }
}
