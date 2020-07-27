package com.senla.multithreading.tasktwo.thread;

import com.senla.carservice.exception.BusinessException;
import com.senla.multithreading.tasktwo.Printer;

public class ThreadOne implements Runnable {
    private final Integer repetitionsNumber;
    private final Integer sleepTime;

    public ThreadOne(Integer repetitionsNumber, Integer sleepTime) {
        this.repetitionsNumber = repetitionsNumber;
        this.sleepTime = sleepTime;
    }

    @Override
    public synchronized void run() {
        if (sleepTime == null){
            throw new BusinessException("");
            //ToDo new exception
        }
        if (repetitionsNumber == null){
            throw new BusinessException("");
            //ToDo new exception
        }
        for (int i = 0; i < repetitionsNumber; i++){
            Printer.printThreadName(Thread.currentThread().getName());
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}