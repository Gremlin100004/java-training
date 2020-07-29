package com.senla.multithreading.taskfour;

import com.senla.multithreading.taskfour.exception.ThreadException;
import com.senla.multithreading.taskfour.thread.ServiceThread;

public class Config {
    public static void run(int sleepTime, int flowDelayTime) {
        ServiceThread serviceThread = new ServiceThread(sleepTime);
        Thread daemonThread = new Thread(serviceThread);
        daemonThread.setDaemon(true);
        daemonThread.start();
        try {
            Thread.sleep(flowDelayTime);
        } catch (InterruptedException e) {
            throw new ThreadException("Error thread sleep");
        }
    }
}