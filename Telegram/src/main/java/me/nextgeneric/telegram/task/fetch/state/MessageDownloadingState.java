package me.nextgeneric.telegram.task.fetch.state;

import lombok.*;

@Value
public class MessageDownloadingState implements ChannelFetchState {

    @NonNull
    private final int counter;

    @NonNull
    private final int total;

    @Override
    public String getDescription() {
        return String.format("%d/%d", counter, total);
    }

}
