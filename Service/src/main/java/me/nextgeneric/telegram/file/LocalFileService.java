package me.nextgeneric.telegram.file;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

public class LocalFileService implements FileService {

    private final Path repository;

    public LocalFileService(String repository) {
        this.repository = Paths.get(repository);
    }

    @Override
    public File getFile(String name, String extension) {

        this.ensureRepositoryExists();

        Path target;
        do {
            String hash = new BigInteger(48, ThreadLocalRandom.current()).toString(16);
            target = repository.resolve(String.format("%d_%s_%s-%s.%s", System.currentTimeMillis(), Thread.currentThread().getName(), hash, name, extension));
        } while (Files.exists(target));

        return target.toFile();
    }

    @Override
    public String getOriginalName(String name) {
        return name.substring(name.lastIndexOf('-') + 1);
    }

    @Override
    public Path getFileLocation(String fileName) {
        return repository.resolve(Paths.get(fileName));
    }

    private void ensureRepositoryExists() {
        if (!Files.exists(repository)) {
            synchronized (repository) {
                if (!Files.exists(repository)) {
                    try {
                        Files.createDirectory(repository);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
            }
        }
    }


}
