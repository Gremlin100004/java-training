package com.senla.multithreading.taskfour;

import com.senla.carservice.exception.BusinessException;

public class ServiceStream implements Runnable {
    private final Integer repetitionsNumber;
    private final Integer sleepTime;

    public ServiceStream(Integer repetitionsNumber, Integer sleepTime) {
        this.repetitionsNumber = repetitionsNumber;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        if (sleepTime == null) {
            throw new BusinessException("");
            //ToDo new exception
        }
        if (repetitionsNumber == null) {
            throw new BusinessException("");
            //ToDo new exception
        }
        for (int i = 0; i < repetitionsNumber; i++) {
            System.out.println(System.currentTimeMillis());
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //ToDo new exception
            }
        }
    }
}