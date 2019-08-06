package me.nextgeneric.telegram.dump;

import com.github.badoualy.telegram.tl.api.TLInputPeerChannel;
import me.nextgeneric.telegram.dump.state.*;
import me.nextgeneric.telegram.dump.state.FailureState;
import me.nextgeneric.telegram.dump.text.TextDump;
import me.nextgeneric.telegram.task.ChannelSearchTask;
import me.nextgeneric.telegram.task.fetch.ChannelFetchTask;
import me.nextgeneric.telegram.task.fetch.MessageFetchResult;

import java.io.File;
import java.util.Optional;

public class ChannelDumpTask {

    private volatile ChannelDumpState state = new InitializingState();

    private final ChannelSearchTask channelSearchTask;
    private final ChannelFetchTask channelFetchTask;
    private final DumpOptions dumpOptions;
    private final TextDumpCreator textDumpCallback;
    private volatile long dateStarted;

    ChannelDumpTask(ChannelSearchTask channelSearchTask, ChannelFetchTask channelFetchService, DumpOptions dumpOptions, TextDumpCreator textDumpCallback) {
        this.channelSearchTask = channelSearchTask;
        this.channelFetchTask = channelFetchService;
        this.dumpOptions = dumpOptions;
        this.textDumpCallback = textDumpCallback;
    }

    public int run() {

        this.state = new SearchingChannelState();

        Optional<TLInputPeerChannel> channelOptional = channelSearchTask.searchChannel(dumpOptions.getChannel());

        int result = 0;

        if (channelOptional.isPresent()) {

            TLInputPeerChannel channelPeer = channelOptional.get();
            this.state = new FetchingMessagesState(channelFetchTask);
            MessageFetchResult messageFetchResult = channelFetchTask.fetch(channelPeer, dumpOptions.isImages(), dumpOptions.getLastMessageId());
            result = messageFetchResult.getLastMessageId();

            if (messageFetchResult.getMessages().isEmpty()) {
                this.state = new FailureState(new NoNewMessagesState());
            } else {
                dump(messageFetchResult);
            }
        } else {
            ChannelDumpState searchState = this.state;
            this.state = new FailureState(searchState);
        }
        return result;
    }

    private void dump(MessageFetchResult messageFetchResult) {
        TextDump textDump = this.textDumpCallback.createTextDump(messageFetchResult.getMessages());
        this.state = new FileCreatingState(textDump);
        textDump.dump();
        this.state = new DoneState();
    }

    public void setStarted() {
        this.dateStarted = System.currentTimeMillis();
    }

    public boolean isCompleted() {
        return state.isDone();
    }

    public DumpOptions getOptions() {
        return dumpOptions;
    }

    public ChannelDumpState getState() {
        return state;
    }

    public File getOutput() {
        return dumpOptions.getTarget();
    }

    public long getDateStarted() {
        return dateStarted;
    }

    public String getFinalDescription() {
        return state.getFinalDescription();
    }
}
