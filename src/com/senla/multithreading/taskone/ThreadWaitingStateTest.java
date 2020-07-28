package com.senla.multithreading.taskone;

public class ThreadWaitingStateTest extends Thread {

    public void run() {
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            System.err.print("ошибка потока1");
        }
    }
}
