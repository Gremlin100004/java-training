package com.senla.multithreading.taskone.statethread.thread;

public class ObservedThread implements Runnable {
    private final Object object;

    public ObservedThread(Object object) {
        this.object = object;
    }

    @Override
    public void run() {
        try {
            synchronized (object) {
                object.wait(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            synchronized (object) {
                object.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}