package me.riguron.telegram.editable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public abstract class EditableDocumentTest {

    private File target;

    @Before
    public void doPrepare() throws IOException {

        final Path path = Paths.get("src", "test", "resources");
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }



        this.target = path.resolve("out." + extension()).toFile();

        try (EditableDocument editableDocument = createDocument(target)) {
            editableDocument.open();
            editableDocument.write("word line 1");
            editableDocument.write("word line 2");
            editableDocument.writeSeparator();
            editableDocument.writeBold("bold line");
        }

    }

    @Test
    public void whenReadWrittenContentThenReadDataIsEqual() {
        try (FileInputStream fis = new FileInputStream(target)) {
            String fileContent = readContent(fis);
            assertNotNull(fileContent);
            assertFalse(fileContent.isEmpty());
            assertTrue(fileContent.contains("word line 1"));
            assertTrue(fileContent.contains("word line 2"));
            assertTrue(fileContent.contains("bold line"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanUp() {
        if (!target.delete()) {
            throw new IllegalStateException("Failed to delete file");
        }
    }

    abstract String extension();

    abstract String readContent(FileInputStream fileInputStream) throws IOException;

    abstract EditableDocument createDocument(File target);

}
