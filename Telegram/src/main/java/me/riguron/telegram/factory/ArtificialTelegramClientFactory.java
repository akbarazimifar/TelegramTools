package me.riguron.telegram.factory;

import com.github.badoualy.telegram.api.TelegramApiStorage;
import com.github.badoualy.telegram.api.TelegramApp;
import com.github.badoualy.telegram.api.TelegramClient;
import me.riguron.telegram.ArtificialTelegramClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class ArtificialTelegramClientFactory implements TelegramClientFactory {

    @Override
    public TelegramClient create(TelegramApp telegramApp, TelegramApiStorage telegramApiStorage) {
        return new ArtificialTelegramClient(telegramApiStorage);
    }
}
