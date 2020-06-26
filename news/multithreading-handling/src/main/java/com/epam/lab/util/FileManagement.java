package com.epam.lab.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManagement {

    public static void moveFileToDirectory(Path from, Path to) throws IOException {
        Files.move(from, to.resolve(from.getFileName()));
    }

    public static void deleteFile(Path path) throws IOException {
        Files.deleteIfExists(path);
    }
}
