package me.nextgeneric.telegram.dump.state;

/**
 * Represents an absence of new messages in a channel.
 */
public class NoNewMessagesState implements ChannelDumpState {

    @Override
    public String publicDescription() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String errorDescription() {
        return "execution.error.no_new_messages";
    }


}
