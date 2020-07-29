package com.senla.multithreading.taskone.thread;

import com.senla.multithreading.taskone.exception.ThreadException;

public class AdditionalThread implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (Exception e){
            throw new ThreadException("Error thread sleep");
        }
    }
}