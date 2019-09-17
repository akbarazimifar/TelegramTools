package me.riguron.telegram;

import com.github.badoualy.telegram.api.TelegramApiStorage;
import com.github.badoualy.telegram.mtproto.auth.AuthKey;
import com.github.badoualy.telegram.mtproto.model.DataCenter;
import com.github.badoualy.telegram.mtproto.model.MTSession;
import lombok.extern.slf4j.Slf4j;
import me.riguron.telegram.entity.UserCredentials;
import me.riguron.telegram.entity.UserDataCenter;
import me.riguron.telegram.repository.UserCredentialsRepository;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Slf4j
public class PersistentApiStorage implements TelegramApiStorage {

    private final String phone;
    private final UserCredentialsRepository userCredentialsRepository;

    private UserCredentials userCredentials;
    private AuthKey authKey;

    public PersistentApiStorage(String phone, UserCredentialsRepository userCredentialsRepository) {
        this.phone = phone;
        this.userCredentialsRepository = userCredentialsRepository;
    }

    @Override
    public synchronized void saveAuthKey(AuthKey authKey) {
        log.info("Saving AuthKey for user {}", phone);
        this.authKey = authKey;
    }

    @Override
    public synchronized void saveDc(DataCenter dataCenter) {
        log.info("Saving DataCenter for user {}", phone);
        this.userCredentials = new UserCredentials(phone, authKey.getKey(), new UserDataCenter(dataCenter.getIp(), dataCenter.getPort()));
        log.info("Saving credentials to database");
        userCredentialsRepository.save(this.userCredentials);
    }


    @Override
    public synchronized AuthKey loadAuthKey() {
        log.info("Loading auth key for user {}", phone);
        Optional<UserCredentials> optional = userCredentialsRepository.findById(phone);
        if (optional.isPresent()) {
            this.userCredentials = optional.get();
            log.info("Loaded auth key for user {}", phone);
        } else {
            log.warn("No auth key present for user {}", phone);
        }
        return userCredentials != null ? new AuthKey(userCredentials.getAuthKey()) : null;
    }

    @Override
    public synchronized DataCenter loadDc() {
        if (this.userCredentials == null) {
            log.warn("Will not load DataCenter for user {} since profile is not present", phone);
            return null;
        }
        UserDataCenter dc = userCredentials.getDataCenter();
        log.info("Loaded DataCenter for user {}", phone);
        return new DataCenter(dc.getHost(), dc.getPort());
    }

    @Override
    public synchronized void deleteAuthKey() {
        log.info("Deleting credentials for {}", phone);
        userCredentialsRepository.delete(userCredentials);
        this.userCredentials = null;
        this.authKey = null;
        log.info("Deleted");
    }

    @Override
    public synchronized void deleteDc() {
        log.info("DeleteDC invoked for user {}", phone);
    }

    @Nullable
    @Override
    public MTSession loadSession() {
        return null;
    }

    @Override
    public void saveSession(MTSession mtSession) {
    }
}
