package com.senla.multithreading.task1;

import com.senla.multithreading.common.exception.ThreadException;

public class Synchronizer {
    public synchronized void waitLiberation() {
        try {
            wait();
        } catch (Exception e) {
            throw new ThreadException("Error wait thread");
        }
    }

    public synchronized void waitLiberationByTime() {
        try {
            wait(1);
        } catch (Exception e) {
            throw new ThreadException("Error wait thread");
        }
    }
    public synchronized void unblockThread(){
        notify();
    }
}