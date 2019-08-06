package me.nextgeneric.telegram.channel;

import lombok.*;

@Value
public class ChannelMessage {

    private final long date;
    private final String text;
    private final byte[] photo;

}
