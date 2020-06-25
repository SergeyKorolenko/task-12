package com.epam.lab.generator;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FullDataGenerator {

    public static final int SUBFOLDERS_COUNT = 9;
    public static final String ROOT_PATH = "/repository/epamlab/news/root";
    public static final String ERROR_PATH = "/repository/epamlab/news/error";

    public static void generate() {
        Path root = Paths.get(ROOT_PATH);
        Path error = Paths.get(ERROR_PATH);
        DirectoryGenerator.generateDirectory(root);
        DirectoryGenerator.generateDirectory(error);
        DirectoryGenerator.generateSubfolders(SUBFOLDERS_COUNT, root);
        DataGenerator.generateData(root);
    }
}
