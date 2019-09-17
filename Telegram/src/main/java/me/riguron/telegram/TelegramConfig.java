package me.riguron.telegram;

import com.github.badoualy.telegram.api.TelegramApp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("app.properties")
@Configuration
public class TelegramConfig {

    @Bean
    public TelegramAppFactoryBean appFactory() {
        return new TelegramAppFactoryBean();
    }

    @Bean
    public TelegramApp telegramApp() {
        return appFactory().getObject();
    }
}
