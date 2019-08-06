package me.nextgeneric.telegram.file;

import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileServiceTest {

    private final static String TARGET = "src/test/resources/directory";

    private FileService fileService = new ValidatingFileService(new LocalFileService(TARGET));

    @Test
    public void whenGetFileThenDirectoryIsCreated() {
        File file = fileService.getFile("name", "pdf");
        assertNotNull(file);
        assertEquals("src/test/resources/directory", file.getParent());
    }

    @Test
    public void whenGetFileAndGetOriginalNameThenEquals() {
        File file = fileService.getFile("name", "pdf");
        assertEquals("name.pdf", fileService.getOriginalName(file.getName()));
    }

    @Test
    public void getOriginalNameNotFormatted() {
        assertEquals("abcde", fileService.getOriginalName("abcde"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOriginalNameBlank() {
        fileService.getOriginalName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOriginalNameNull() {
        fileService.getOriginalName(null);
    }

    @Test
    public void getFileLocation() {
        Path location = fileService.getFileLocation("name.pdf");
        assertEquals(Paths.get(TARGET + "/name.pdf"), location);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFileLocationNull() {
        fileService.getFileLocation("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFileLocationBlank() {
        fileService.getFileLocation(null);
    }

    @AfterClass
    public static void doCleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(TARGET));
    }
}