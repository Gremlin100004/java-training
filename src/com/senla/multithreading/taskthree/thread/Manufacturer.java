package com.senla.multithreading.taskthree.thread;

import com.senla.multithreading.taskthree.repository.RepositoryNumber;
import com.senla.multithreading.taskthree.util.Randomizer;

public class Manufacturer implements Runnable {
    private RepositoryNumber repositoryNumber;

    public Manufacturer(final RepositoryNumber repositoryNumber) {
        this.repositoryNumber = repositoryNumber;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 100){
            int randomNumber = Randomizer.getRandomNumber();
            repositoryNumber.addRandomNumber(randomNumber);
            System.out.println("Thread manufacture add number");
            i++;
        }
    }
}