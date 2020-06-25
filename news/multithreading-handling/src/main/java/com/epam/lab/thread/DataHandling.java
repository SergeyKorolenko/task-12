package com.epam.lab.thread;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.filereader.JsonFileReader;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DataHandling {

    public static final int THREAD_COUNT = 5;
    public static final int TEST_TIME = 10;
    public static final int FILES_COUNT = 2;
    public static final int PERIOD_TIME = 80;
    public static final long SCAN_DELAY = 100;
    public static final String ROOT_PATH = "/repository/epamlab/news/root";

    public static void handle() {
        List<Path> files = collectFile(Paths.get(ROOT_PATH));
        BlockingQueue<Path> blockingQueue = new ArrayBlockingQueue<>(files.size(), true, files);
        ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);
        for (Path path : blockingQueue) {
            es.submit(new FileHandling(path));
        }
        es.shutdown();
        try {
            es.awaitTermination(60,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static List<Path> collectFile(Path rootDirectory) {
        List<Path> files = new ArrayList<>();
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(rootDirectory)) {
            for (Path path : stream) {
                if(path.toFile().isDirectory()) {
                    files.addAll(collectFile(path));
                } else {
                    files.add(path);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}
