package me.nextgeneric.telegram.confirm;

import com.github.badoualy.telegram.tl.api.TLUser;
import com.github.badoualy.telegram.tl.api.auth.TLSentCode;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;
import lombok.extern.slf4j.Slf4j;
import me.nextgeneric.telegram.SystemRoles;
import me.nextgeneric.telegram.entity.UserCredentials;
import me.nextgeneric.telegram.entity.UserProfile;
import me.nextgeneric.telegram.repository.UserCredentialsRepository;
import me.nextgeneric.telegram.repository.UserProfileRepository;
import me.nextgeneric.telegram.user.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class ConfirmationService {

    private final UserCredentialsRepository userCredentialsRepository;

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public ConfirmationService(UserCredentialsRepository userCredentialsRepository, UserProfileRepository userProfileRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public boolean sendCode(UserSession userSession) {
        try {
            TLSentCode sentCode = userSession.getTelegramClient().authSendCode(false, userSession.getPhoneNumber(), true);
            userSession.setRegistrationHash(sentCode.getPhoneCodeHash());
            return true;
        } catch (RpcErrorException | IOException e) {
            log.error("Failed to sent code", e);
            return false;
        }
    }

    @Transactional
    public int confirm(UserSession userSession, int code) {

        TLUser user;

        try {
            user = userSession.getTelegramClient().authSignIn(userSession.getPhoneNumber(), userSession.getRegistrationHash(), String.valueOf(code)).getUser().getAsUser();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        Optional<UserCredentials> userCredentials = userCredentialsRepository.findById(userSession.getPhoneNumber());

        if (!userCredentials.isPresent()) {
            throw new IllegalStateException("No credentials set for current user " + userSession);
        }

        Optional<UserProfile> existingProfileOptional = userProfileRepository.findByUsernameWithCredentials(userSession.getPhoneNumber());

        if (existingProfileOptional.isPresent()) {
            existingProfileOptional.get().getUserCredentials().refresh(userCredentials.get());
        } else {
            userProfileRepository.save(new UserProfile(
                    user.getId(), user.getFirstName() + " " + user.getLastName(),
                    userSession.getPhoneNumber(),
                    SystemRoles.ROLE_FULL, userCredentials.get()));
        }

        userSession.setRegistrationHash(null);
        return user.getId();

    }


}
