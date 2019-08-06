package me.nextgeneric.telegram.dump;

import com.github.badoualy.telegram.api.TelegramClient;
import me.nextgeneric.telegram.DocumentType;
import me.nextgeneric.telegram.EditableDocumentFactory;
import me.nextgeneric.telegram.date.DateFormat;
import me.nextgeneric.telegram.editable.EditableDocument;
import me.nextgeneric.telegram.entity.UserProfile;
import me.nextgeneric.telegram.file.LocalFileService;
import org.junit.Test;
import org.springframework.context.MessageSource;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChannelDumpTaskFactoryTest {

    @Test
    public void createChannelDumpTask() throws IOException {

        File target = new File("out.txt");
        LocalFileService fileService = mock(LocalFileService.class);
        when(fileService.getFile(any(), any())).thenReturn(target);

        EditableDocumentFactory editableDocumentFactory = mock(EditableDocumentFactory.class);

        ChannelDumpTaskFactory channelDumpTaskFactory = new ChannelDumpTaskFactory(
                mock(MessageSource.class),
                editableDocumentFactory,
                mock(DateFormat.class),
                fileService
        );

        when(editableDocumentFactory.createDocument(any(), any())).thenReturn(Optional.of(
                mock(EditableDocument.class)
        ));

        DumpRequest dumpRequest =

                new DumpRequest(
                        "channel",
                        DocumentType.PDF,
                        true,
                        true,
                        Locale.ENGLISH
                );


        UserProfile userProfile = mock(UserProfile.class);
        when(userProfile.getLastMessageId(eq("channel"))).thenReturn(5);

        ChannelDumpTask dumpTask = channelDumpTaskFactory.createChannelDumpTask(
                dumpRequest,
                userProfile,
                mock(TelegramClient.class)
        );

        assertEquals(target, dumpTask.getOutput());

        assertEquals(dumpRequest.getChannelName(), dumpTask.getOptions().getChannel());
        assertEquals(dumpRequest.getDocumentType(), dumpTask.getOptions().getDocumentType());
        assertEquals(dumpRequest.isLoadOnlyNew(), dumpTask.getOptions().getLastMessageId() == 5);

    }
}