package me.riguron.telegram.projection;

import lombok.Value;
import me.riguron.telegram.DocumentType;
import me.riguron.telegram.DocumentType;

@Value
public class TaskProjection {

    private final long date;
    private final String channelName;
    private final boolean images;
    private final DocumentType documentType;
    private final String file;
    private final String state;
    private final boolean completed;

}
