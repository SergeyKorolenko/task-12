package com.epam.lab.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DirectoryGenerator {

    private static final String SEPARATOR = "/";
    private static final int DIVIDER = 3;
    private static final int FACTOR = 2;
    private static final int MIN_DEPTH = 1;

    public static void generateDirectory(Path directory) {
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateSubfolders(int subfoldersCount, Path rootFolder) {
        int count = 0;
        int tempSubfoldersCount = subfoldersCount;
        Random random = new Random();
        List<Path> pathList = new ArrayList<>();
        while (count < subfoldersCount) {
            int countFolder = (int) (Math.random() * (tempSubfoldersCount / DIVIDER * FACTOR - MIN_DEPTH) + MIN_DEPTH)
                    + MIN_DEPTH;
            count += countFolder;
            tempSubfoldersCount -= countFolder;
            if (tempSubfoldersCount < countFolder) {
                countFolder = tempSubfoldersCount;
            }
            for (int i = 0; i < countFolder; i++) {
                Path path = Paths.get(rootFolder.toString() + SEPARATOR + i);
                pathList.add(path);
                generateDirectory(path);
            }
            int sub = random.ints(0, pathList.size()).findFirst().getAsInt();
            rootFolder = pathList.get(sub);
            pathList.remove(sub);
        }
    }
}
