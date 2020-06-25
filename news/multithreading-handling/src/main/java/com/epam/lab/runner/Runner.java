package com.epam.lab.runner;

import com.epam.lab.generator.FullDataGenerator;
import com.epam.lab.thread.DataHandling;

public class Runner {

    public static void main(String[] args) {
        FullDataGenerator.generate();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DataHandling.handle();
    }

}
