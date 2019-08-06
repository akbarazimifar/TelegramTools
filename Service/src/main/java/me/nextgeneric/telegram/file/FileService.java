package me.nextgeneric.telegram.file;

import java.io.File;
import java.nio.file.Path;

public interface FileService {

    File getFile(String name, String extension);

    String getOriginalName(String name);

    Path getFileLocation(String fileName);

}
