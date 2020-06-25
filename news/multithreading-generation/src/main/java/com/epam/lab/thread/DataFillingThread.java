package com.epam.lab.thread;

import com.epam.lab.filewriter.JsonFileWriter;
import com.epam.lab.generator.FileGenerator;
import com.epam.lab.generator.JsonDataGenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataFillingThread implements Runnable {

    private final Path path;
    private final int fileCount;

    private static final int MIN_COUNT_OF_DATA = 3;
    private static final int MAX_COUNT_OF_DATA = 5;

    public DataFillingThread(Path path, int fileCount) {
        this.path = path;
        this.fileCount = fileCount;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.fileCount; i++) {
            try {
                Path filePath = FileGenerator.createFile(path);
                List<String> data = new ArrayList<>();
                int dataCount = (int) (Math.random() * (MAX_COUNT_OF_DATA - MIN_COUNT_OF_DATA)) + MIN_COUNT_OF_DATA;
                for (int j = 0; j < dataCount; j++) {
                    data.add(JsonDataGenerator.generateJson());
                }
                JsonFileWriter.writeToFile(filePath, data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
