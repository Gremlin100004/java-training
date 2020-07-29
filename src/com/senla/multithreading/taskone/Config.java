package com.senla.multithreading.taskone;

import com.senla.multithreading.taskone.exception.ThreadException;
import com.senla.multithreading.taskone.thread.AdditionalThread;
import com.senla.multithreading.taskone.thread.ObservedThread;

public class Config {
    private static final Object synchronizationObject = new Object();

    public static void run() {
        Thread additionalThread = new Thread(new AdditionalThread());
        Thread observedThread = new Thread(new ObservedThread(synchronizationObject));
        System.out.println(observedThread.getState());
        additionalThread.start();
        observedThread.start();
        System.out.println(observedThread.getState());
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            throw new ThreadException("Error thread sleep");
        }
        System.out.println(observedThread.getState());
        synchronized (synchronizationObject){
            synchronizationObject.notify();
        }
        System.out.println(observedThread.getState());
        System.out.println(observedThread.getState());
        synchronized (synchronizationObject){
            synchronizationObject.notify();
        }
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            throw new ThreadException("Error thread sleep");
        }
        System.out.println(observedThread.getState());
    }
}