package com.senla.multithreading.task2;

import com.senla.multithreading.task2.repository.FlagRepository;
import com.senla.multithreading.task2.runnable.RunnableImpl;

public class Main {
    public static void main(String[] args) {
        int numberOfCycles = 10;
        FlagRepository resource = new FlagRepository();
        Thread firstThread = new Thread(new RunnableImpl(resource, numberOfCycles, true));
        firstThread.setName("First thread");
        firstThread.start();
        Thread secondThread = new Thread(new RunnableImpl(resource, numberOfCycles, false));
        secondThread.setName("Second thread");
        secondThread.start();
    }
}