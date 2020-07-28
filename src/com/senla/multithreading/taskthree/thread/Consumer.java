package com.senla.multithreading.taskthree.thread;

import com.senla.multithreading.taskthree.repository.RepositoryNumber;

public class Consumer implements Runnable {
    private RepositoryNumber repositoryNumber;

    public Consumer(RepositoryNumber repositoryNumber) {
        this.repositoryNumber = repositoryNumber;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 100) {
            repositoryNumber.pullOutRandomNumber();
//            System.out.println(randomNumber);
            i++;
        }
    }
}