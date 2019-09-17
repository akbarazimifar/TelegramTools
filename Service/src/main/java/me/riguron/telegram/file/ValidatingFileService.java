package me.riguron.telegram.file;

import java.io.File;
import java.nio.file.Path;

public class ValidatingFileService implements FileService {

    private final FileService origin;

    public ValidatingFileService(FileService origin) {
        this.origin = origin;
    }

    @Override
    public File getFile(String name, String extension) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name can not be blank or empty");
        }

        if (extension == null || extension.isEmpty()) {
            throw new IllegalArgumentException("Extension can not be empty");
        }

        return origin.getFile(name, extension);
    }

    @Override
    public String getOriginalName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("File name can not be empty");
        }
        return origin.getOriginalName(name);
    }

    @Override
    public Path getFileLocation(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Target file name can not be null or empty");
        }
        return origin.getFileLocation(fileName);
    }
}
