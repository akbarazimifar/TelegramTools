package me.riguron.telegram.task.fetch.state;

public class TaskNotStartedState implements ChannelFetchState {

    @Override
    public String getDescription() {
        return "channel.fetch.not_started";
    }
}
