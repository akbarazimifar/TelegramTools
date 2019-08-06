package me.nextgeneric.telegram.task;

import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.tl.api.TLAbsChat;
import com.github.badoualy.telegram.tl.api.TLChannel;
import com.github.badoualy.telegram.tl.api.TLChat;
import com.github.badoualy.telegram.tl.api.TLInputPeerChannel;
import com.github.badoualy.telegram.tl.api.messages.TLAbsDialogs;
import com.github.badoualy.telegram.tl.api.messages.TLDialogs;
import com.github.badoualy.telegram.tl.core.TLVector;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChannelSearchTaskTest {

    private ChannelSearchTask channelSearchTask;

    private TelegramClient telegramClient;

    @Before
    public void prepareChannels() throws IOException, RpcErrorException {
        this.telegramClient = mock(TelegramClient.class);

        TLAbsDialogs dialogs = new TLDialogs();
        TLVector<TLAbsChat> chats = new TLVector<>();

        chats.add(new TLChat());
        chats.add(this.newChannel("channel_1"));
        chats.add(this.newChannel("channel_2"));
        dialogs.setChats(chats);

        when(telegramClient.messagesGetDialogs(eq(true), eq(0), eq(0), any(), eq(100))).
                thenReturn(dialogs);

        this.channelSearchTask = new ChannelSearchTask(telegramClient);
    }

    @Test
    public void whenSearchExistingChannelThenSearched() {
        searchAndAssert("channel_1", true);
        searchAndAssert("channel_2", true);
    }

    @Test
    public void whenSearchInexistentChannelThenDoesNotExist() {
        searchAndAssert("channel_3", false);
    }

    @Test
    public void whenSearchChannelIgnoreCaseThenExists() {
        searchAndAssert("ChAnnEl_2", true);
        searchAndAssert("CHANNEL_1", true);
        searchAndAssert("cHanNel_3", false);
    }

    @Test
    public void whenExceptionFiredThenEmpty() throws IOException, RpcErrorException {
        searchButThrow(IOException.class, "channel_1");
        searchButThrow(RpcErrorException.class, "channel_2");
    }

    private void searchButThrow(Class<? extends Throwable> type, String name) throws IOException, RpcErrorException {
        when(telegramClient.messagesGetDialogs(eq(true), eq(0), eq(0), any(), eq(100))).thenThrow(type);
        searchAndAssert(name, false);
    }

    private void searchAndAssert(String name, boolean shouldExist) {
        Optional<TLInputPeerChannel> channelOptional = channelSearchTask.searchChannel(name);
        assertEquals(shouldExist, channelOptional.isPresent());
    }

    private TLChannel newChannel(String name) {
        TLChannel tlChannel = new TLChannel();
        tlChannel.setUsername(name);
        tlChannel.setAccessHash(Long.MAX_VALUE);
        return tlChannel;
    }


}