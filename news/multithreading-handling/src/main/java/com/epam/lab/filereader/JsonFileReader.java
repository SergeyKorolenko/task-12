package com.epam.lab.filereader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonFileReader {

    public static List<String> readFromFile(Path path) throws IOException {
        List<String> jsonData;
        try (Stream<String> stream = Files.lines(path)) {
            jsonData = stream.collect(Collectors.toList());
        }
        return jsonData;
    }
}
