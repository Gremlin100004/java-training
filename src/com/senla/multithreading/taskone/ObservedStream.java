package com.senla.multithreading.taskone;

public class ObservedStream implements Runnable {

    @Override
    public void run() {
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            System.err.print("ошибка потока");
        }
    }
}
