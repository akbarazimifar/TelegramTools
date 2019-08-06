package me.nextgeneric.telegram;

import com.github.badoualy.telegram.mtproto.auth.AuthKey;
import com.github.badoualy.telegram.mtproto.model.DataCenter;
import me.nextgeneric.telegram.entity.UserCredentials;
import me.nextgeneric.telegram.entity.UserDataCenter;
import me.nextgeneric.telegram.repository.UserCredentialsRepository;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PersistentApiStorageTest {

    private static final AuthKey AUTH_KEY = new AuthKey(new byte[256]);

    private static final String PHONE = "phone";

    private UserCredentialsRepository userCredentialsRepository = mock(UserCredentialsRepository.class);

    @Test
    public void whenSaveAuthKeyThenSaved() {
        PersistentApiStorage persistentApiStorage = new PersistentApiStorage(PHONE, userCredentialsRepository);
        persistentApiStorage.saveAuthKey(AUTH_KEY);
        UserCredentials userCredentials = new UserCredentials(PHONE, AUTH_KEY.getKey(), new UserDataCenter("ip", 11110));
        persistentApiStorage.saveDc(new DataCenter("ip", 11111));
        verify(userCredentialsRepository).save(userCredentials);
    }


    @Test
    public void whenAuthKeyExistsThenLoadedAuthKeyAndDc() {
        UserCredentials userCredentials = mock(UserCredentials.class);
        when(userCredentialsRepository.findById(eq(PHONE))).thenReturn(Optional.of(userCredentials));
        when(userCredentials.getAuthKey()).thenReturn(AUTH_KEY.getKey());
        when(userCredentials.getDataCenter()).thenReturn(new UserDataCenter("host", 222));

        PersistentApiStorage persistentApiStorage = new PersistentApiStorage(PHONE, userCredentialsRepository);

        AuthKey loadedKey = persistentApiStorage.loadAuthKey();
        assertNotNull(loadedKey);
        assertArrayEquals(userCredentials.getAuthKey(), loadedKey.getKey());

        DataCenter dataCenter = persistentApiStorage.loadDc();
        assertNotNull(dataCenter);
        assertEquals(dataCenter.getIp(), "host");
        assertEquals(dataCenter.getPort(), 222);
    }

    @Test
    public void whenProfileDoesNotExistThenAuthKeyAndDcAreNull() {
        when(userCredentialsRepository.findById(eq(PHONE))).thenReturn(Optional.empty());

        PersistentApiStorage persistentApiStorage = new PersistentApiStorage(PHONE, userCredentialsRepository);

        AuthKey loadedKey = persistentApiStorage.loadAuthKey();
        DataCenter dataCenter = persistentApiStorage.loadDc();

        assertNull(loadedKey);
        assertNull(dataCenter);

    }

    @Test
    public void whenDeleteAuthKeyThenDeleted() {
        PersistentApiStorage persistentApiStorage = new PersistentApiStorage(PHONE, userCredentialsRepository);
        persistentApiStorage.deleteAuthKey();
        persistentApiStorage.deleteDc();
        verify(userCredentialsRepository).delete(any());
    }

}