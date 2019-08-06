package me.nextgeneric.telegram.dump;

import lombok.Value;
import me.nextgeneric.telegram.DocumentType;

import java.io.File;

@Value
public class DumpOptions {

    private final String channel;
    private final DocumentType documentType;
    private final boolean images;
    private final File target;
    private final int lastMessageId;
}
