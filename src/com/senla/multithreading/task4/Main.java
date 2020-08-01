package com.senla.multithreading.task4;

import com.senla.multithreading.common.exception.ThreadException;
import com.senla.multithreading.task4.thread.ServiceThread;

public class Main {
    public static void main(String[] args) {
        int sleepTime = 1;
        int flowDelayTime = 10000;
        ServiceThread serviceThread = new ServiceThread(sleepTime);
        serviceThread.setDaemon(true);
        serviceThread.start();
        try {
            Thread.sleep(flowDelayTime);
        } catch (InterruptedException e) {
            throw new ThreadException("Error thread sleep");
        }
    }
}