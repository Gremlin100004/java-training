package com.senla.multithreading.task1;

import com.senla.multithreading.common.exception.ThreadException;
import com.senla.multithreading.task1.runnable.RunnableImpl;

public class Main {
    public static void main(String[] args) {
        Thread observedThread = new Thread(new RunnableImpl(Main.class));
        showStages(observedThread);
    }

    private static void showStages(Thread observedThread) {
        showStageNew(observedThread);
        showStageRunnable(observedThread);
        showStageWaiting(observedThread);
        showStageBlocked(observedThread);
        showStageTimedWaiting(observedThread);
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

    private static void showStageBlocked(Thread observedThread) {
        // если что, статик методы как раз синхронизируются по классу, можно попробовать
        // просто пометить метод synchronized
        synchronized (Main.class) {
            Main.class.notify();
        }
        System.out.println(observedThread.getState());
    }

    private static void showStageTimedWaiting(Thread observedThread) {
        System.out.println(observedThread.getState());
        synchronized (Main.class) {
            Main.class.notify();
        }
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