package com.senla.multithreading.task3.thread;

import com.senla.multithreading.common.exception.ThreadException;
import com.senla.multithreading.task3.repository.NumberRepository;

public class Consumer extends Thread {
    private final NumberRepository numberRepository;
    private final Integer numberOfCycles;

    public Consumer(NumberRepository numberRepository, int numberOfCycles) {
        this.numberRepository = numberRepository;
        this.numberOfCycles = numberOfCycles;
        validate();
    }

    // порядок методов в классе
    private void validate() {
        if (numberRepository == null) {
            throw new ThreadException("Error object NumberRepository");
        }
        if (numberOfCycles == null) {
            throw new ThreadException("Repeat number error");
        }
    }

    @Override
    public void run() {
        int i = 0;
        while (i < numberOfCycles) {
            int randomNumber = numberRepository.pullOutRandomNumber();
            System.out.println("Consumer get number: " + randomNumber);
            i++;
        }
    }
}