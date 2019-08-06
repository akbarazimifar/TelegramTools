package me.nextgeneric.telegram.dump.state;

import me.nextgeneric.telegram.task.fetch.ChannelFetchTask;

import java.util.Collections;
import java.util.List;

/**
 * Represents a period of time when messages are being fetched
 * from the channel to be dumped to pdf further.
 */
public class FetchingMessagesState implements ChannelDumpState {

    private final ChannelFetchTask fetchTask;

    public FetchingMessagesState(ChannelFetchTask fetchTask) {
        this.fetchTask = fetchTask;
    }

    @Override
    public String errorDescription() {
        return "execution.error.messages.loading";
    }

    @Override
    public String publicDescription() {
        return "execution.state.messages_loading";
    }

    @Override
    public List<Object> getStateVariables() {
        return Collections.singletonList(fetchTask.getChannelFetchState().getDescription());
    }
}
