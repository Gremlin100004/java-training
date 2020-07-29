package com.senla.multithreading.taskthree.thread;

import com.senla.multithreading.taskthree.repository.RepositoryNumber;
import com.senla.multithreading.taskthree.util.Randomizer;

public class Manufacturer implements Runnable {
    private final RepositoryNumber repositoryNumber;
    private final Integer numberOfCycles;
    private final Integer min;
    private final Integer max;

    public Manufacturer(RepositoryNumber repositoryNumber, int numberOfCycles, int min, int max) {
        this.repositoryNumber = repositoryNumber;
        this.numberOfCycles = numberOfCycles;
        this.min = min;
        this.max = max;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < numberOfCycles) {
            int randomNumber = Randomizer.getRandomNumber(min, max);
            repositoryNumber.addRandomNumber(randomNumber);
            System.out.println("manufacturer create number: " + randomNumber);
            i++;
        }
    }
}