package com.senla.multithreading.taskfour;

import com.senla.multithreading.taskfour.thread.ServiceThread;

public class Main {
    public static void main(String[] args) {
        int sleepTime = 1;
        int flowDelayTime = 10000;
        ServiceThread serviceThread = new ServiceThread(sleepTime);
        Thread daemonThread = new Thread(serviceThread);
        daemonThread.setDaemon(true);
        daemonThread.start();
        try {
            Thread.sleep(flowDelayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}