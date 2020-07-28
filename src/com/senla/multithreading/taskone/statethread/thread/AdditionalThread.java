package com.senla.multithreading.taskone.statethread.thread;

public class AdditionalThread implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}