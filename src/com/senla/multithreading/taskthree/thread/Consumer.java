package com.senla.multithreading.taskthree.thread;

import com.senla.multithreading.taskthree.repository.RepositoryNumber;

public class Consumer implements Runnable {
    private final RepositoryNumber repositoryNumber;
    private final Integer numberOfCycles;

    public Consumer(RepositoryNumber repositoryNumber, int numberOfCycles) {
        this.repositoryNumber = repositoryNumber;
        this.numberOfCycles = numberOfCycles;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < numberOfCycles) {
            int randomNumber = repositoryNumber.pullOutRandomNumber();
            System.out.println("Consumer get number: " + randomNumber);
            i++;
        }
    }
}