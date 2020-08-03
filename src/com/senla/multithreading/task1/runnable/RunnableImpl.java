package com.senla.multithreading.task1.runnable;

import com.senla.multithreading.common.exception.ThreadException;

public class RunnableImpl implements Runnable {
    private final Object synchronizationObject;

    public RunnableImpl(Object object) {
        this.synchronizationObject = object;
    }

    @Override
    public void run() {
        waitLiberation();
        waitLiberationByTime();
    }

    private void waitLiberation() {
        synchronized (synchronizationObject) {
            try {
                synchronizationObject.wait();
            } catch (Exception e) {
                throw new ThreadException("Error wait thread");
            }
        }
    }

    private void waitLiberationByTime() {
        synchronized (synchronizationObject) {
            try {
                synchronizationObject.wait(1);
            } catch (Exception e) {
                throw new ThreadException("Error wait thread");
            }
        }
    }
}