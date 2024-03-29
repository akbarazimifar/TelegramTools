package me.riguron.telegram.auth;

import com.github.badoualy.telegram.api.TelegramApp;
import me.riguron.telegram.PersistentApiStorage;
import me.riguron.telegram.factory.TelegramClientFactory;
import me.riguron.telegram.repository.UserCredentialsRepository;
import me.riguron.telegram.user.UserSession;
import me.riguron.telegram.validate.PhoneNumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private PhoneNumberFormatter phoneNumberFormatter;

    private UserSession userSession;

    private TelegramApp telegramApp;

    private UserCredentialsRepository userCredentialsRepository;

    private TelegramClientFactory telegramClientFactory;

    @Autowired
    public AuthSuccessHandler(PhoneNumberFormatter phoneNumberFormatter, UserSession userSession, TelegramApp telegramApp, UserCredentialsRepository userCredentialsRepository, TelegramClientFactory telegramClientFactory) {
        this.phoneNumberFormatter = phoneNumberFormatter;
        this.userSession = userSession;
        this.telegramApp = telegramApp;
        this.userCredentialsRepository = userCredentialsRepository;
        this.telegramClientFactory = telegramClientFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        String phoneNumber = phoneNumberFormatter.transform(authentication.getName());
        userSession.setTelegramClient(telegramClientFactory.create(telegramApp, new PersistentApiStorage(phoneNumber, userCredentialsRepository)));
        userSession.setPhoneNumber(phoneNumber);
        httpServletResponse.sendRedirect("/application");
    }
}
