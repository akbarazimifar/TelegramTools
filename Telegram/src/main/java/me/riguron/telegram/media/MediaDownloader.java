package me.riguron.telegram.media;

import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.api.utils.MediaInput;
import com.github.badoualy.telegram.api.utils.TLMediaUtilsKt;
import com.github.badoualy.telegram.tl.api.TLAbsMessageMedia;
import com.github.badoualy.telegram.tl.api.TLMessage;
import com.github.badoualy.telegram.tl.api.TLMessageMediaPhoto;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class MediaDownloader {

    private final TelegramClient telegramClient;

    public MediaDownloader(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public byte[] download(TLMessage tlAbsMessage, AttachmentType type) {
        byte[] result = new byte[0];
        try {
            if (tlAbsMessage.getMedia() != null) {
                TLAbsMessageMedia media = tlAbsMessage.getMedia();
                if (media != null && (type == AttachmentType.ANY || media.getClass().equals(type.attachmentClass))) {
                    MediaInput mediaInput = TLMediaUtilsKt.getAbsMediaInput(media);
                    if (mediaInput != null) {
                        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                            telegramClient.downloadSync(mediaInput.getInputFileLocation(), mediaInput.getSize(), outputStream);
                            result = outputStream.toByteArray();
                        }
                    }
                }
            }
        } catch (RpcErrorException | IOException e) {
            log.error("Error downloading attachment", e);
        }
        return result;
    }


    public enum AttachmentType {

        PHOTO(TLMessageMediaPhoto.class), ANY();

        private Class<? extends TLAbsMessageMedia> attachmentClass;

        AttachmentType() {
        }

        AttachmentType(Class<? extends TLAbsMessageMedia> attachmentClass) {
            this.attachmentClass = attachmentClass;
        }

    }

}
