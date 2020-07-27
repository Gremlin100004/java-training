package com.senla.multithreading.taskthree.thread;

import com.senla.multithreading.taskthree.repository.RepositoryNumber;
import com.senla.multithreading.taskthree.service.ConsumerService;
import com.senla.multithreading.taskthree.util.Randomizer;

public class Consumer implements Runnable {
//    private ConsumerService consumerService;
    private RepositoryNumber repositoryNumber;

    public Consumer(RepositoryNumber repositoryNumber) {
        this.repositoryNumber = repositoryNumber;
    }

    @Override
    public synchronized void run() {
        int i = 0;
        while (i < 100){
            repositoryNumber.pullOutRandomNumber();
//            System.out.println(randomNumber);
            i++;
        }
    }
}