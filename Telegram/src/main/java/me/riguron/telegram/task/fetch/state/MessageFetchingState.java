package me.riguron.telegram.task.fetch.state;

public class MessageFetchingState implements ChannelFetchState {

    @Override
    public String getDescription() {
        return "channel.fetch.loading_messages";
    }
}
