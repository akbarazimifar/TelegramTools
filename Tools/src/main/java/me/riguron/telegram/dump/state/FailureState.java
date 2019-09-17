package me.riguron.telegram.dump.state;

import java.util.List;

/**
 * Represents a state for channel dump task that has
 * failed for some reason.
 */
public class FailureState implements ChannelDumpState {

    private final ChannelDumpState failedState;

    public FailureState(ChannelDumpState failedState) {
        this.failedState = failedState;
    }

    @Override
    public String errorDescription() {
        return failedState.errorDescription();
    }

    @Override
    public String publicDescription() {
       throw new UnsupportedOperationException();
    }

    @Override
    public List<Object> getStateVariables() {
        return failedState.getStateVariables();
    }
}
