package com.senla.multithreading.task1;

import com.senla.multithreading.common.exception.ThreadException;
import com.senla.multithreading.task1.runnable.Observable;

public class Main {
    public static void main(String[] args) {
        Synchronizer synchronizer = new Synchronizer();
        Thread observedThread = new Thread(new Observable(synchronizer));
        showStages(observedThread, synchronizer);
    }

    private static void showStages(Thread observedThread, Synchronizer synchronizer) {
        showStageNew(observedThread);
        showStageRunnable(observedThread);
        showStageWaiting(observedThread);
        showStageBlocked(observedThread, synchronizer);
        showStageTimedWaiting(observedThread, synchronizer);
        showStageTerminated(observedThread);
    }

    private static void showStageNew(Thread observedThread) {
        System.out.println(observedThread.getState());
    }

    private static void showStageRunnable(Thread observedThread) {
        observedThread.start();
        System.out.println(observedThread.getState());
    }

    private static void showStageWaiting(Thread observedThread) {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            throw new ThreadException("Error thread sleep");
        }
        System.out.println(observedThread.getState());
    }

    private static synchronized void showStageBlocked(Thread observedThread, Synchronizer synchronizer) {
        synchronizer.unblockThread();
        System.out.println(observedThread.getState());
    }

    private static synchronized void showStageTimedWaiting(Thread observedThread, Synchronizer synchronizer) {
        System.out.println(observedThread.getState());
        synchronizer.unblockThread();
    }

    private static void showStageTerminated(Thread observedThread) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new ThreadException("Error thread sleep");
        }
        System.out.println(observedThread.getState());
    }
}