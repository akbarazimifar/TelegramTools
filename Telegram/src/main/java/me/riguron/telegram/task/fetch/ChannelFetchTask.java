package me.riguron.telegram.task.fetch;

import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.tl.api.*;
import com.github.badoualy.telegram.tl.core.TLVector;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;
import me.riguron.telegram.channel.ChannelMessage;
import me.riguron.telegram.media.MediaDownloader;
import me.riguron.telegram.task.fetch.state.*;
import me.riguron.telegram.channel.ChannelMessage;
import me.riguron.telegram.media.MediaDownloader;
import me.riguron.telegram.task.fetch.state.ChannelFetchState;
import me.riguron.telegram.task.fetch.state.MessageDownloadingState;
import me.riguron.telegram.task.fetch.state.MessageFetchingState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ChannelFetchTask {

    private volatile ChannelFetchState channelFetchState = new TaskNotStartedState();

    private final MediaDownloader mediaDownloader;
    private final TelegramClient telegramClient;

    public ChannelFetchTask(MediaDownloader mediaDownloader, TelegramClient telegramClient) {
        this.mediaDownloader = mediaDownloader;
        this.telegramClient = telegramClient;
    }

    public MessageFetchResult fetch(TLInputPeerChannel channel, boolean downloadImages, int minId) {

        try {
            this.channelFetchState = new MessageFetchingState();

            List<TLMessage> list = fetchMessages(channel, minId);
            AtomicInteger atomicInteger = new AtomicInteger();

            this.channelFetchState = new MessageDownloadingState(atomicInteger.get(), list.size());

            List<ChannelMessage> messages = list.stream().map(tlMessage -> {
                String text;
                TLAbsMessageMedia media = tlMessage.getMedia();

                if (media instanceof TLMessageMediaPhoto) {
                    TLMessageMediaPhoto messageMediaPhoto = (TLMessageMediaPhoto) media;
                    text = messageMediaPhoto.getCaption();
                } else {
                    text = tlMessage.getMessage();
                }

                byte[] photo = downloadImages ? mediaDownloader.download(tlMessage, MediaDownloader.AttachmentType.PHOTO) : new byte[0];

                atomicInteger.incrementAndGet();

                return new ChannelMessage((long) tlMessage.getDate() * 1000, text, photo);
            }).collect(Collectors.toList());

            return new MessageFetchResult(list.isEmpty() ? 0 : list.get(0).getId(), messages);
        } finally {
            this.channelFetchState = new TaskCompletedState();
        }
    }

    private List<TLMessage> fetchMessages(TLInputPeerChannel channel, int minId) {
        int offset = 0;
        TLVector<TLAbsMessage> list;
        List<TLMessage> result = new ArrayList<>();
        do {
            try {
                list = telegramClient.messagesGetHistory(channel, 0, 0, offset, 100, 0, minId).getMessages();

                result.addAll(list.stream().filter(message -> message instanceof TLMessage)
                        .map(tlAbsMessage -> (TLMessage) tlAbsMessage).
                                collect(Collectors.toList()));
                offset += 100;
            } catch (RpcErrorException | IOException e) {
                e.printStackTrace();
                break;
            }

        } while (!list.isEmpty());
        return result;
    }

    public ChannelFetchState getChannelFetchState() {
        return channelFetchState;
    }

}
