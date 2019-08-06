package me.nextgeneric.telegram.dump;

import lombok.NonNull;
import me.nextgeneric.telegram.channel.ChannelMessage;
import me.nextgeneric.telegram.dump.text.TextDump;

import java.util.List;

public interface TextDumpCreator {

    TextDump createTextDump(@NonNull List<ChannelMessage> messages);
}
