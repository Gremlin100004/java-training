package com.senla.multithreading.taskfour.thread;

import com.senla.multithreading.taskfour.exception.ThreadException;

public class ServiceThread implements Runnable {
    private final Integer millisecondsSleepTime;

    public ServiceThread(int secondsSleepTime) {
        this.millisecondsSleepTime = secondsSleepTime * 1000;
    }

    @Override
    public void run() {
        while (true){
            System.out.println(String.format("%1$TH:%1$TM:%1$TS", System.currentTimeMillis()));
            try {
                Thread.sleep(millisecondsSleepTime);
            } catch (InterruptedException e) {
                throw new ThreadException("Error thread sleep");
            }
        }
    }
}