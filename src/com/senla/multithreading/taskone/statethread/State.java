package com.senla.multithreading.taskone.statethread;

import com.senla.multithreading.taskone.statethread.thread.AdditionalThread;
import com.senla.multithreading.taskone.statethread.thread.ObservedThread;

public class State {
    private static final Object synchronizationObject = new Object();

    public static void run() {
        try {
            Thread additionalThread = new Thread(new AdditionalThread());
            Thread observedThread = new Thread(new ObservedThread(synchronizationObject));
            System.out.println(observedThread.getState());
            additionalThread.start();
            observedThread.start();
            System.out.println(observedThread.getState());
            Thread.sleep(500);
            System.out.println(observedThread.getState());
            synchronized (synchronizationObject){
                synchronizationObject.notify();
            }
            System.out.println(observedThread.getState());
            System.out.println(observedThread.getState());
            synchronized (synchronizationObject){
                synchronizationObject.notify();
            }
            Thread.sleep(2000);
            System.out.println(observedThread.getState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
