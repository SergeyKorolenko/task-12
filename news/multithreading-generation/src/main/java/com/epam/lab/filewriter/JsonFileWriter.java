package com.epam.lab.filewriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonFileWriter {

    public static void writeToFile(Path path, List<String> data) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String json : data) {
                writer.write(json + System.lineSeparator());
            }
        }
    }
}
