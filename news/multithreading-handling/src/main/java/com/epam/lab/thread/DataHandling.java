package com.epam.lab.thread;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

public class DataHandling {

    static {
        Properties properties = new Properties();
        try {
            properties.load(DataHandling.class.getClassLoader().getResourceAsStream("handling.properties"));
            THREAD_COUNT = Integer.parseInt(properties.getProperty("threadCount"));
            SCAN_DELAY = Integer.parseInt(properties.getProperty("scanDelay"));
        }
        catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    public static int THREAD_COUNT;
    public static long SCAN_DELAY;
    public static final String ROOT_PATH = "/repository/epamlab/news/root";

    public static void handle() {
        List<Path> files = collectFile(Paths.get(ROOT_PATH));
        BlockingQueue<Path> paths = new ArrayBlockingQueue<>(files.size(), true, files);
        ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);
        for (Path path : paths) {
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
