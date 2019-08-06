package me.nextgeneric.telegram.auth;

import com.github.badoualy.telegram.api.TelegramApp;
import me.nextgeneric.telegram.factory.TelegramClientFactory;
import me.nextgeneric.telegram.repository.UserCredentialsRepository;
import me.nextgeneric.telegram.user.UserSession;
import me.nextgeneric.telegram.validate.PhoneNumberFormatter;
import org.junit.Test;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AuthSuccessHandlerTest {

    @Test
    public void onAuthenticationSuccess() throws IOException {

        PhoneNumberFormatter phoneNumberFormatter = mock(PhoneNumberFormatter.class);
        UserSession userSession = mock(UserSession.class);


        AuthSuccessHandler devAuthSuccessHandler = new AuthSuccessHandler(
                phoneNumberFormatter,
                userSession,
                new TelegramApp(1, "", "", "", "", ""),
                mock(UserCredentialsRepository.class),
                mock(TelegramClientFactory.class)
        );

        Authentication authentication = mock(Authentication.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(phoneNumberFormatter.transform(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        devAuthSuccessHandler.onAuthenticationSuccess(
                mock(HttpServletRequest.class),
                response,
                authentication
        );

        verify(userSession).setTelegramClient(any());
        verify(userSession).setPhoneNumber(any());
        verify(response).sendRedirect(eq("/application"));

    }
}