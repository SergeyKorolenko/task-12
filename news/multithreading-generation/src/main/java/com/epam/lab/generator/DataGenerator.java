package com.epam.lab.generator;

import com.epam.lab.thread.DataFillingThread;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataGenerator {

    private static final int FILES_COUNT = 2;

    public static void generateData(Path rootDirectory) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootDirectory)) {
            for (Path path : stream) {
                if (path.toFile().isDirectory()) {
                    Thread thread = new Thread(new DataFillingThread(path, FILES_COUNT));
                    thread.start();
                    generateData(path);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
