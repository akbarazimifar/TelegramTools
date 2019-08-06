package me.nextgeneric.telegram.dump;

import lombok.Value;

@Value
public class DumpResult {

    private final ChannelDumpTask task;
    private final int id;

}
