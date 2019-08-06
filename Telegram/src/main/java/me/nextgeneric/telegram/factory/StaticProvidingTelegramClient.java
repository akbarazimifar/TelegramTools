package me.nextgeneric.telegram.factory;

import com.github.badoualy.telegram.api.Kotlogram;
import com.github.badoualy.telegram.api.TelegramApiStorage;
import com.github.badoualy.telegram.api.TelegramApp;
import com.github.badoualy.telegram.api.TelegramClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class StaticProvidingTelegramClient implements TelegramClientFactory {

    @Override
    public TelegramClient create(TelegramApp telegramApp, TelegramApiStorage telegramApiStorage) {
        return Kotlogram.getDefaultClient(telegramApp, telegramApiStorage);
    }
}
