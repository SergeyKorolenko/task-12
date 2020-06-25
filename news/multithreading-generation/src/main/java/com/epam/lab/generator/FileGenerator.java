package com.epam.lab.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileGenerator {

    private static final String FILE_EXTENSION = ".txt";
    private static final String SEPARATOR = "/";
    private static final String FILE_NAME_SEPARATOR = "-";

    private static String createFileName() {
        return UUID.randomUUID().toString() + FILE_NAME_SEPARATOR + UUID.randomUUID().toString() + FILE_EXTENSION;
    }

    public static Path createFile(Path path) throws IOException {
        Path fullFilePath = Paths.get(path.toString() + SEPARATOR + createFileName());
        return Files.createFile(fullFilePath);
    }
}
