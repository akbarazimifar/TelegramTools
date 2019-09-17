package me.riguron.telegram.channel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChannelParserTest {

    private final ChannelParser parser = new ChannelParser();

    @Test
    public void whenTrimmedStringThenReplacesWhitespaces() {

        assertEquals("channel_", parser.parse("channel_ "));
        assertEquals("channel_", parser.parse(" channel_ "));
        assertEquals("channel_", parser.parse(" channel_"));

    }

    @Test
    public void whenNameContainsIllegalSymbolsThenTheyAreReplaced() {

        assertEquals("channel_", parser.parse("cha-+nnel_ "));
        assertEquals("channel_", parser.parse(" channel==_ "));
        assertEquals("channel_", parser.parse("c//hannel_ "));
        assertEquals("channel_", parser.parse("cha.nn,el_ "));
    }
}