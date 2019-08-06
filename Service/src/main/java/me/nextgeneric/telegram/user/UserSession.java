package me.nextgeneric.telegram.user;

import com.github.badoualy.telegram.api.TelegramClient;
import lombok.Data;

import javax.annotation.PreDestroy;

@Data
public class UserSession {

    private String phoneNumber;

    private TelegramClient telegramClient;

    private String registrationHash;

    private int userId;

    @PreDestroy
    public void terminateClient() {
        if (telegramClient != null) {
            telegramClient.close();
        }
    }


}
