package com.senla.multithreading.taskone.thread;

import com.senla.multithreading.taskone.exception.ThreadException;

public class ObservedThread implements Runnable {
    private final Object synchronizationObject;

    public ObservedThread(Object object) {
        this.synchronizationObject = object;
    }

    @Override
    public void run() {
        try {
            synchronized (synchronizationObject) {
                synchronizationObject.wait();
            }
        } catch (Exception e) {
            throw new ThreadException("Error wait thread");
        }
        try {
            synchronized (synchronizationObject) {
                synchronizationObject.wait(2000);
            }
        } catch (Exception e) {
            throw new ThreadException("Error wait thread");
        }
    }
}