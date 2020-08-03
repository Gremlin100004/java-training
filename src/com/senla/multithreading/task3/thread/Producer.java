package com.senla.multithreading.task3.thread;

import com.senla.multithreading.common.exception.ThreadException;
import com.senla.multithreading.task3.repository.NumberRepository;

import java.util.Random;

public class Producer extends Thread {
    private static final Random RANDOM = new Random();
    private final NumberRepository numberRepository;
    private final Integer numberOfCycles;
    private final Integer min;
    private final Integer max;

    public Producer(NumberRepository numberRepository, int numberOfCycles, int min, int max) {
        this.numberRepository = numberRepository;
        this.numberOfCycles = numberOfCycles;
        this.min = min;
        this.max = max;
        validate();
    }

    private void validate() {
        if (numberRepository == null) {
            throw new ThreadException("Error object NumberRepository");
        }
        if (numberOfCycles == null) {
            throw new ThreadException("Repeat number error");
        }
        if (min == null) {
            throw new ThreadException("Error minimum value");
        }
        if (max == null) {
            throw new ThreadException("Error maximum value");
        }
    }

    @Override
    public void run() {
        int i = 0;
        while (i < numberOfCycles) {
            int randomNumber = RANDOM.nextInt(max - min + 1) + min;
            numberRepository.addRandomNumber(randomNumber);
            System.out.println("Producer create number: " + randomNumber);
            i++;
        }
    }
}