package com.epam.lab.thread;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.filereader.JsonFileReader;
import com.epam.lab.util.FileManagement;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandling implements Runnable {

    private Path path;

    public static final String ERROR_PATH = "/repository/epamlab/news/error";

    public FileHandling(Path path) {
        this.path = path;
    }


    @Override
    public void run() {
        System.out.println("Thread is working : " + Thread.currentThread().getName()
                + " with file : " + path.getFileName());
        try {
            List<String> data = JsonFileReader.readFromFile(path);
            Gson gson = new Gson();
            List<NewsDto> newsDtos = data.stream()
                    .map(n -> gson.fromJson(n, NewsDto.class)).collect(Collectors.toList());
            FileManagement.deleteFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            try {
                FileManagement.moveFileToDirectory(path, Paths.get(ERROR_PATH));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
