package com.senla.multithreading.task2;

import com.senla.multithreading.task2.repository.FlagRepository;

public class Main {
    public static void main(String[] args) {
        int numberOfCycles = 10;
        FlagRepository resource = new FlagRepository();
        Thread firstThread = new Thread(() -> {
            int i = 0;
            while (i < numberOfCycles) {
                if (resource.getFlag()) {
                    System.out.println(Thread.currentThread().getName());
                    resource.changeFlag();
                    i++;
                }
            }
        });
        firstThread.setName("First thread");
        firstThread.start();
        Thread secondThread = new Thread(() -> {
            int i = 0;
            while (i < numberOfCycles) {
                if (!resource.getFlag()) {
                    System.out.println(Thread.currentThread().getName());
                    resource.changeFlag();
                    i++;
                }
            }
        });
        secondThread.setName("Second thread");
        secondThread.start();
    }
}