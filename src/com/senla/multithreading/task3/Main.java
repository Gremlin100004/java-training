package com.senla.multithreading.task3;

import com.senla.multithreading.task3.repository.NumberRepository;
import com.senla.multithreading.task3.thread.Consumer;
import com.senla.multithreading.task3.thread.Producer;

public class Main {
    public static void main(String[] args) {
        int bufferLimit = 20;
        int numberOfCycles = 100;
        int min = 0;
        int max = 100;
        NumberRepository repositoryNumber = new NumberRepository(bufferLimit);
        Consumer consumer = new Consumer(repositoryNumber, numberOfCycles);
        Producer producer = new Producer(repositoryNumber, numberOfCycles, min, max);
        producer.start();
        consumer.start();
    }
}