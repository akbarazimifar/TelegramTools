package me.riguron.telegram.editable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public abstract class EditableDocumentTest {

    private File target;

    @Before
    public void doPrepare() {

        this.target = Paths.get("src", "test", "resources").resolve("out." + extension()).toFile();

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
        if (!this.target.delete()) {
            throw new IllegalStateException("Failed to delete test output, please do it manually");
        }
    }

    abstract String extension();

    abstract String readContent(FileInputStream fileInputStream) throws IOException;

    abstract EditableDocument createDocument(File target);

}
