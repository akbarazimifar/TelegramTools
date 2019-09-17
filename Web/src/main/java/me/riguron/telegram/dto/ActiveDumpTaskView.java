package me.riguron.telegram.dto;

import lombok.Value;

@Value
public class ActiveDumpTaskView {

    private final String date;
    private final String channelName;
    private final String documentType;
    private final String state;

}
