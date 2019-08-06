package me.nextgeneric.telegram.dump.state;

public class InitializingState implements ChannelDumpState {

    @Override
    public String errorDescription() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String publicDescription() {
        return "execution.state.initializing";
    }
}
