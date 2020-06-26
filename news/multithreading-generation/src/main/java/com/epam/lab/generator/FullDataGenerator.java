package com.epam.lab.generator;

import com.epam.lab.thread.DataFillingTask;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FullDataGenerator {

    static {
        Properties properties = new Properties();
        try {
            properties.load(FullDataGenerator.class.getClassLoader().getResourceAsStream("generation.properties"));
            SUBFOLDERS_COUNT = Integer.parseInt(properties.getProperty("subfoldersCount"));
            TEST_TIME = Integer.parseInt(properties.getProperty("testTime"));
            FILES_COUNT = Integer.parseInt(properties.getProperty("filesCount"));
        }
        catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    public static int SUBFOLDERS_COUNT;
    public static int TEST_TIME;
    private static int FILES_COUNT;

    public static final String ROOT_PATH = "/repository/epamlab/news/root";
    public static final String ERROR_PATH = "/repository/epamlab/news/error";
    public static final long DELAY = 0L;

    public static void generate() {
        Path root = Paths.get(ROOT_PATH);
        Path error = Paths.get(ERROR_PATH);
        DirectoryGenerator.generateDirectory(root);
        DirectoryGenerator.generateDirectory(error);
        DirectoryGenerator.generateSubfolders(SUBFOLDERS_COUNT, root);
        List<Path> paths = collectPath(root);
        paths.add(root);
        List<Timer> timers = new ArrayList<>();
        for (Path path : paths) {
            TimerTask timerTask = new DataFillingTask(path, FILES_COUNT);
            Timer timer = new Timer();
            timers.add(timer);
            timer.schedule(timerTask, DELAY);
        }
        try {
            Thread.sleep(TEST_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Timer timer : timers) {
            timer.cancel();
        }
    }

    private static List<Path> collectPath(Path rootDirectory) {
        List<Path> paths = new ArrayList<>();
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(rootDirectory)) {
            for (Path path : stream) {
                if(path.toFile().isDirectory()) {
                    paths.add(path);
                    paths.addAll(collectPath(path));
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return paths;
    }
}
