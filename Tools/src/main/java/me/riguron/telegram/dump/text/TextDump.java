package me.riguron.telegram.dump.text;

import me.riguron.telegram.channel.ChannelMessage;
import me.riguron.telegram.date.DateFormat;
import me.riguron.telegram.editable.EditableDocument;
import me.riguron.telegram.channel.ChannelMessage;
import me.riguron.telegram.date.DateFormat;
import me.riguron.telegram.editable.EditableDocument;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class TextDump {

    private final AtomicInteger dumped = new AtomicInteger();

    private final List<ChannelMessage> messages;
    private final EditableDocument document;
    private final DateFormat dateFormat;
    private final MessageSource messageSource;
    private final Locale locale;

    public TextDump(List<ChannelMessage> messages, EditableDocument editableDocument, DateFormat dateFormat, MessageSource messageSource, Locale locale) {
        this.messages = messages;
        this.document = editableDocument;
        this.dateFormat = dateFormat;
        this.messageSource = messageSource;
        this.locale = locale;
    }

    public void dump() {
        try {
            document.open();
            messages.forEach(channelMessage -> {
                if (!channelMessage.getText().isEmpty() || channelMessage.getPhoto().length > 0) {
                    document.writeBold(messageSource.getMessage("dump.output.message.date", new Object[]{dateFormat.format(channelMessage.getDate(), locale)}, locale));
                    write(channelMessage, document);
                    document.writeSeparator();
                }
                dumped.incrementAndGet();
            });
        } finally {
            document.close();
        }
    }

    private void write(ChannelMessage tlMessage, EditableDocument document) {

        String text = tlMessage.getText();

        if (!text.isEmpty()) {
            document.write(text);
        }

        byte[] photo = tlMessage.getPhoto();

        if (photo.length > 0) {
            document.writeImage(photo);
        }

    }

    public TextDumpProgress getStatus() {
        return new TextDumpProgress(dumped.get(), messages.size());
    }

}
