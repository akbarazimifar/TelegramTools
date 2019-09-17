package me.riguron.telegram.dump.state;

/**
 * Represents a period of time when specified channel is being searched
 * in the Telegram database.
 */
public class SearchingChannelState implements ChannelDumpState {

    @Override
    public String errorDescription() {
        return "execution.error.undefined_channel";
    }

    @Override
    public String publicDescription() {
        return "execution.state.channel.search";
    }

}
