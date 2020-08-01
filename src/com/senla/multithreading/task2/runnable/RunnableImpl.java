package com.senla.multithreading.task2.runnable;

import com.senla.multithreading.common.exception.ThreadException;
import com.senla.multithreading.task2.repository.FlagRepository;

public class RunnableImpl implements Runnable {
    private final FlagRepository resource;
    private final Integer numberOfCycles;
    private final Boolean printFlag;

    public RunnableImpl(FlagRepository resource, int numberOfCycles, boolean printFlag) {
        this.resource = resource;
        this.numberOfCycles = numberOfCycles;
        this.printFlag = printFlag;
        validate();
    }

    private void validate() {
        if (resource == null) {
            throw new ThreadException("Resource error");
        }
        if (printFlag == null) {
            throw new ThreadException("Sleep time error");
        }
        if (numberOfCycles == null) {
            throw new ThreadException("Repeat number error");
        }
    }

    @Override
    public void run() {
        int i = 0;
        while (i < numberOfCycles) {
            if (resource.getFlag() == printFlag){
                System.out.println(Thread.currentThread().getName());
                resource.changeFlag();
                i++;
            }
        }
    }
}