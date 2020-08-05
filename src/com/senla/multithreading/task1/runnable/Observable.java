package com.senla.multithreading.task1.runnable;

import com.senla.multithreading.task1.Synchronizer;

public class Observable implements Runnable {
    private final Synchronizer synchronizer;

    public Observable(Synchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }

    @Override
    public void run() {
        synchronizer.waitLiberation();
        synchronizer.waitLiberationByTime();
    }
}