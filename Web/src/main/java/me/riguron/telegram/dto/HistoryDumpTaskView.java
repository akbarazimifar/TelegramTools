package me.riguron.telegram.dto;

import lombok.Value;
import me.riguron.telegram.DocumentType;
import me.riguron.telegram.DocumentType;

@Value
public class HistoryDumpTaskView {

    private final String date;
    private final String channel;
    private final boolean images;
    private final DocumentType documentType;
    private final String fileId;
    private final String state;
    private final boolean completed;


}
