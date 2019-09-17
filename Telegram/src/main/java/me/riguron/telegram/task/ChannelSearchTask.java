package me.riguron.telegram.task;

import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.tl.api.TLAbsChat;
import com.github.badoualy.telegram.tl.api.TLChannel;
import com.github.badoualy.telegram.tl.api.TLInputPeerChannel;
import com.github.badoualy.telegram.tl.api.TLInputPeerEmpty;
import com.github.badoualy.telegram.tl.api.messages.TLAbsDialogs;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ChannelSearchTask {

    private static final Logger logger = LoggerFactory.getLogger(ChannelSearchTask.class);

    private final TelegramClient telegramClient;

    public ChannelSearchTask(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public Optional<TLInputPeerChannel> searchChannel(String channelName) {
        Optional<TLInputPeerChannel> result = Optional.empty();

        try {
            TLAbsDialogs tlAbsDialogs = telegramClient.messagesGetDialogs(true, 0, 0, new TLInputPeerEmpty(), 100);
            List<TLAbsChat> chats = tlAbsDialogs.getChats();

            for (TLAbsChat chat : chats) {
                if (chat instanceof TLChannel) {
                    TLChannel tlChannel = (TLChannel) chat;
                    String userName = tlChannel.getUsername();
                    if (channelName.equalsIgnoreCase(userName)) {
                        TLInputPeerChannel tlInputPeerChannel = new TLInputPeerChannel();
                        tlInputPeerChannel.setChannelId(tlChannel.getId());
                        tlInputPeerChannel.setAccessHash(tlChannel.getAccessHash());
                        result = Optional.of(tlInputPeerChannel);
                        break;
                    }
                }
            }
        } catch (RpcErrorException | IOException e) {
            logger.error("Error while searching channel", e);
        }
        return result;
    }

}
