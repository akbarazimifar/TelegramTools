package me.nextgeneric.telegram;

import com.github.badoualy.telegram.api.TelegramApp;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;

public class TelegramAppFactoryBean implements FactoryBean<TelegramApp> {

    @Value("${api.id}")
    private int apiId;

    @Value("${api.hash}")
    private String apiHash;

    @Value("${app.version}")
    private String appVersion;

    @Value("${model}")
    private String model;

    @Value("${system.version}")
    private String systemVersion;

    @Value("${lang.code}")
    private String langCode;


    @NotNull
    @Override
    public TelegramApp getObject() {
        return new TelegramApp(apiId, apiHash, model, systemVersion, appVersion, langCode);
    }

    @NotNull
    @Override
    public Class<?> getObjectType() {
        return TelegramApp.class;
    }
}