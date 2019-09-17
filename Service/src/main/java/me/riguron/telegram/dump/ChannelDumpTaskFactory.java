package me.riguron.telegram.dump;

import com.github.badoualy.telegram.api.TelegramClient;
import me.riguron.telegram.EditableDocumentFactory;
import me.riguron.telegram.date.DateFormat;
import me.riguron.telegram.dump.text.TextDump;
import me.riguron.telegram.editable.EditableDocument;
import me.riguron.telegram.entity.UserProfile;
import me.riguron.telegram.file.FileService;
import me.riguron.telegram.media.MediaDownloader;
import me.riguron.telegram.task.ChannelSearchTask;
import me.riguron.telegram.task.fetch.ChannelFetchTask;
import me.riguron.telegram.EditableDocumentFactory;
import me.riguron.telegram.date.DateFormat;
import me.riguron.telegram.editable.EditableDocument;
import me.riguron.telegram.entity.UserProfile;
import me.riguron.telegram.file.FileService;
import me.riguron.telegram.media.MediaDownloader;
import me.riguron.telegram.task.ChannelSearchTask;
import me.riguron.telegram.task.fetch.ChannelFetchTask;
import org.springframework.context.MessageSource;

import java.io.File;
import java.util.Optional;


public class ChannelDumpTaskFactory {

    private final MessageSource messageSource;

    private final EditableDocumentFactory documentFactory;

    private final DateFormat dateFormat;

    private final FileService fileService;

    public ChannelDumpTaskFactory(MessageSource messageSource, EditableDocumentFactory documentFactory, DateFormat dateFormat, FileService fileService) {
        this.messageSource = messageSource;
        this.documentFactory = documentFactory;
        this.dateFormat = dateFormat;
        this.fileService = fileService;
    }

    public ChannelDumpTask createChannelDumpTask(DumpRequest request, UserProfile userProfile, TelegramClient telegramClient) {

        File file = fileService.getFile(request.getChannelName(), request.getDocumentType().getExtension());

        Optional<EditableDocument> editableDocumentOptional = documentFactory.createDocument(request.getDocumentType(), file);

        if (!editableDocumentOptional.isPresent()) {
            throw new IllegalStateException("Failed to create document of type " + request.getDocumentType());
        }

        return new ChannelDumpTask(
                new ChannelSearchTask(telegramClient),
                new ChannelFetchTask(new MediaDownloader(telegramClient), telegramClient),
                new DumpOptions(
                        request.getChannelName(), request.getDocumentType(), request.isImages(), file,
                        request.isLoadOnlyNew() ? userProfile.getLastMessageId(request.getChannelName()) : 0
                ),
                messages -> new TextDump(messages, editableDocumentOptional.get(), dateFormat, messageSource, request.getLocale())
        );

    }
}
