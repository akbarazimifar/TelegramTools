package me.nextgeneric.telegram.task.fetch.state;

public class TaskCompletedState implements ChannelFetchState {

    @Override
    public String getDescription() {
        return "channel.fetch.done";
    }
}
