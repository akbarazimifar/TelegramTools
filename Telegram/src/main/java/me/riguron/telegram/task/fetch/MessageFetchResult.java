package me.riguron.telegram.task.fetch;

import lombok.NonNull;
import lombok.Value;
import me.riguron.telegram.channel.ChannelMessage;

import java.util.List;

@Value
public class MessageFetchResult {

    @NonNull
    private final int lastMessageId;

    @NonNull
    private final List<ChannelMessage> messages;



}
