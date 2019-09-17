package me.riguron.telegram.dump.state;

/**
 * Represents a successful Dump completion state.
 */
public class DoneState implements ChannelDumpState {

    @Override
    public String errorDescription() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String publicDescription() {
        return "execution.state.completed";
    }

    @Override
    public boolean isDone() {
        return true;
    }
}
