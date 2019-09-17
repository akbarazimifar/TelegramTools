package me.riguron.telegram.task.fetch;

import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.tl.api.*;
import com.github.badoualy.telegram.tl.api.messages.TLAbsMessages;
import com.github.badoualy.telegram.tl.api.messages.TLMessages;
import com.github.badoualy.telegram.tl.core.TLVector;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;
import me.riguron.telegram.channel.ChannelMessage;
import me.riguron.telegram.media.MediaDownloader;
import me.riguron.telegram.task.fetch.state.TaskCompletedState;
import me.riguron.telegram.task.fetch.state.TaskNotStartedState;
import me.riguron.telegram.channel.ChannelMessage;
import me.riguron.telegram.media.MediaDownloader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ChannelFetchTaskTest {

    private static final int ID_OFFSET = 5;

    private static final int EXPECTED_TIME_S = 1555320390;

    private static final long EXPECTED_TIME_MILLIS = 1555320390000L;

    private ChannelFetchTask channelFetchTask;

    @Before
    public void doPrepare() throws IOException, RpcErrorException {

        MediaDownloader mediaDownloader = mock(MediaDownloader.class);
        TelegramClient telegramClient = mock(TelegramClient.class);

        when(mediaDownloader.download(argThat(tlMessage -> tlMessage.getMedia() != null), eq(MediaDownloader.AttachmentType.PHOTO))).thenReturn(new byte[]{1, 2, 3});

        this.channelFetchTask = spy(new ChannelFetchTask(mediaDownloader, telegramClient));

        TLAbsMessages absMessages = new TLMessages();
        TLVector<TLAbsMessage> messages = new TLVector<>();
        absMessages.setMessages(messages);

        for (int i = 0; i < 1000; i++) {
            messages.add(newMessage(i));
        }

        when(telegramClient.messagesGetHistory(any(), eq(0), eq(0), anyInt(), eq(100), eq(0), anyInt())).thenAnswer(invocationOnMock -> {

            int messageOffset = invocationOnMock.getArgument(3);
            int upperBound = messageOffset + (int) invocationOnMock.getArgument(4);

            TLVector<TLAbsMessage> vector = new TLVector<>();

            if (upperBound <= messages.size()) {
                vector.addAll(messages.subList(messageOffset, messageOffset + (int) invocationOnMock.getArgument(4)));
            }

            TLAbsMessages tlAbsMessages = new TLMessages();
            tlAbsMessages.setMessages(vector);
            return tlAbsMessages;
        });

        assertEquals(TaskNotStartedState.class, channelFetchTask.getChannelFetchState().getClass());
    }

    private TLAbsMessage newMessage(int i) {

        TLMessage tlMessage = new TLMessage();
        TLMessageMediaPhoto tlMessageMediaPhoto = new TLMessageMediaPhoto();

        if (i % 2 == 0) {
            tlMessage.setMedia(tlMessageMediaPhoto);
            tlMessageMediaPhoto.setCaption("caption-" + i);
            tlMessageMediaPhoto.setPhoto(new TLPhoto());
        } else {
            tlMessage.setMessage("message-" + i);
        }

        tlMessage.setId(ID_OFFSET + i);
        tlMessage.setDate(EXPECTED_TIME_S);
        return tlMessage;
    }


    @Test
    public void fetch() {

        MessageFetchResult messageFetchResult = channelFetchTask.fetch(new TLInputPeerChannel(), true, 0);

        assertEquals(TaskCompletedState.class, channelFetchTask.getChannelFetchState().getClass());

        assertNotNull(messageFetchResult);
        assertEquals(ID_OFFSET, messageFetchResult.getLastMessageId());

        List<ChannelMessage> messages = messageFetchResult.getMessages();
        assertNotNull(messages);

        assertEquals(1000, messages.size());
        for (int i = 0; i < 1000; i++) {
            ChannelMessage channelMessage = messages.get(i);
            if (i % 2 == 0) {
                byte[] photo = channelMessage.getPhoto();
                assertNotNull(photo);
                assertArrayEquals(new byte[]{1, 2, 3}, photo);
            } else {
                assertEquals("message-" + i, channelMessage.getText());
            }
            assertEquals(EXPECTED_TIME_MILLIS, channelMessage.getDate());

        }
    }

}