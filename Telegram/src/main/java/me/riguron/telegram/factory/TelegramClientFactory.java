package me.riguron.telegram.factory;

import com.github.badoualy.telegram.api.TelegramApiStorage;
import com.github.badoualy.telegram.api.TelegramApp;
import com.github.badoualy.telegram.api.TelegramClient;

public interface TelegramClientFactory {

    TelegramClient create(TelegramApp telegramApp, TelegramApiStorage telegramApiStorage);
}
