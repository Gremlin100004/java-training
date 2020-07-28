package com.senla.multithreading.tasktwo.names.thread;

import com.senla.multithreading.tasktwo.names.exception.ThreadException;

public class ThreadPrinter implements Runnable {
    private final Integer repetitionsNumber;
    private final Integer sleepTime;

    public ThreadPrinter(Integer repetitionsNumber, Integer sleepTime) {
        this.repetitionsNumber = repetitionsNumber;
        this.sleepTime = sleepTime;
    }

    @Override
    public synchronized void run() {
        if (sleepTime == null) {
            throw new ThreadException("Sleep time error");
        }
        if (repetitionsNumber == null) {
            throw new ThreadException("Repeat number error");
        }
        for (int i = 0; i < repetitionsNumber; i++) {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new ThreadException("Repeat number error");
            }
        }
    }
}