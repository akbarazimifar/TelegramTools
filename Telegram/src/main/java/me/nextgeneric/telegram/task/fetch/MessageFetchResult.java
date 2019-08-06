package me.nextgeneric.telegram.task.fetch;

import lombok.*;
import me.nextgeneric.telegram.channel.ChannelMessage;

import java.util.List;

@Value
public class MessageFetchResult {

    @NonNull
    private final int lastMessageId;

    @NonNull
    private final List<ChannelMessage> messages;



}
