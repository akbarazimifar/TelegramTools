package me.riguron.telegram.dump.text;

import me.riguron.telegram.channel.ChannelMessage;
import me.riguron.telegram.date.DateFormat;
import me.riguron.telegram.editable.EditableDocument;
import org.junit.Test;
import org.springframework.context.MessageSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Locale;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TextDumpTest {

    @Test
    public void doTest() throws IOException {

        MessageSource messageSource = mock(MessageSource.class);
        EditableDocument editableDocument = mock(EditableDocument.class);


        final Path path = Paths.get("src", "test", "resources");
        if (Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        File outputTarget = path.resolve("out.pdf").toFile();


        try {
            assertTrue(outputTarget.createNewFile());
            TextDump textDump = new TextDump(
                    Collections.singletonList(new ChannelMessage(12342342L, "text", new byte[]{1, 2, 3})),
                    editableDocument, mock(DateFormat.class), messageSource, Locale.ENGLISH);
            textDump.dump();

            verify(editableDocument).open();
            verify(editableDocument).writeBold(any());
            verify(editableDocument).writeSeparator();
            verify(editableDocument).close();
            verify(editableDocument).writeImage(eq(new byte[]{1, 2, 3}));

        } finally {
            //noinspection ResultOfMethodCallIgnored
            outputTarget.delete();
        }
    }
}