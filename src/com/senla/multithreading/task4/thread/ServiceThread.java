package com.senla.multithreading.task4.thread;

import com.senla.multithreading.common.exception.ThreadException;

public class ServiceThread extends Thread {
    private final Integer millisecondsSleepTime;

    public ServiceThread(int sleepTimeInSeconds) {
        this.millisecondsSleepTime = sleepTimeInSeconds * 1000;
        validate();
    }

    private void validate() {
        if (millisecondsSleepTime == null) {
            throw new ThreadException("Value error, sleep time in milliseconds");
        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.printf("%1$TH:%1$TM:%1$TS%n", System.currentTimeMillis());
            try {
                Thread.sleep(millisecondsSleepTime);
            } catch (InterruptedException e) {
                throw new ThreadException("Error thread sleep");
            }
        }
    }
}