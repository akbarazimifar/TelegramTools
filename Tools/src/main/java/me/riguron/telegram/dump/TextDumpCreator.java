package me.riguron.telegram.dump;

import lombok.NonNull;
import me.riguron.telegram.channel.ChannelMessage;
import me.riguron.telegram.dump.text.TextDump;

import java.util.List;

public interface TextDumpCreator {

    TextDump createTextDump(@NonNull List<ChannelMessage> messages);
}
