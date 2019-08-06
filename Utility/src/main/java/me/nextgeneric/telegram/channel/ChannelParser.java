package me.nextgeneric.telegram.channel;

import org.springframework.stereotype.Component;

@Component
public class ChannelParser {

    public String parse(String channel) {
        return channel.trim().replaceAll("[^a-zA-Z0-9_]", "");
    }
}

