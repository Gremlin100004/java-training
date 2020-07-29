package com.senla.multithreading.tasktwo.thread;

import com.senla.multithreading.tasktwo.exception.ThreadException;

public class Printer implements Runnable {
    private final Integer numberOfCycles;
    private final Integer sleepTime;

    public Printer(Integer numberOfCycles, Integer sleepTime) {
        this.numberOfCycles = numberOfCycles;
        this.sleepTime = sleepTime;
    }

    @Override
    public synchronized void run() {
        if (sleepTime == null) {
            throw new ThreadException("Sleep time error");
        }
        if (numberOfCycles == null) {
            throw new ThreadException("Repeat number error");
        }
        for (int i = 0; i < numberOfCycles; i++) {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new ThreadException("Error thread sleep");
            }
        }
    }
}