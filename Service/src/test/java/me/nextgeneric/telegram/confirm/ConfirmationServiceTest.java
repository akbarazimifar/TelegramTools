package me.nextgeneric.telegram.confirm;

import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.tl.api.auth.TLAbsCodeType;
import com.github.badoualy.telegram.tl.api.auth.TLAbsSentCodeType;
import com.github.badoualy.telegram.tl.api.auth.TLSentCode;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;
import me.nextgeneric.telegram.repository.UserCredentialsRepository;
import me.nextgeneric.telegram.repository.UserProfileRepository;
import me.nextgeneric.telegram.user.UserSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import java.io.IOException;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ConfirmationServiceTest {

    private ConfirmationService confirmationService;

    private UserProfileRepository userProfileRepository = mock(UserProfileRepository.class);

    private UserCredentialsRepository userCredentialsRepository = mock(UserCredentialsRepository.class);

    @Before
    public void construct() {
        this.confirmationService = new ConfirmationService(userCredentialsRepository, userProfileRepository);
    }

    @Test
    public void whenSuccessfulSendCodeThenSetHash() {
        genericTestSendCode(x -> x.thenReturn(new TLSentCode(false, mock(TLAbsSentCodeType.class), "hash", mock(TLAbsCodeType.class), 0)), true, userSession -> verify(userSession).setRegistrationHash(eq("hash")));
    }

    @Test
    public void whenClientThrowsExceptionThenReturnsFalse() {
        genericTestSendCode(tlSentCodeOngoingStubbing -> tlSentCodeOngoingStubbing.thenThrow(new RpcErrorException(1, "tag")), false, userSession -> verify(userSession, times(0)).setRegistrationHash(any()));
    }

    private void genericTestSendCode(Consumer<OngoingStubbing<TLSentCode>> clientConfig, boolean expectedResult, Consumer<UserSession> afterSent) {

        UserSession userSession = mock(UserSession.class);
        TelegramClient client = mock(TelegramClient.class);
        when(userSession.getTelegramClient()).thenReturn(client);

        try {
            clientConfig.accept(when(client.authSendCode(eq(false), any(), anyBoolean())));
        } catch (RpcErrorException | IOException e) {
            throw new IllegalStateException(e);
        }


        assertEquals(expectedResult, confirmationService.sendCode(userSession));
        afterSent.accept(userSession);
    }
}