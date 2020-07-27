package com.senla.multithreading.taskthree;

import com.senla.multithreading.taskthree.repository.RepositoryNumber;
import com.senla.multithreading.taskthree.repository.RepositoryNumberImpl;
import com.senla.multithreading.taskthree.thread.Consumer;
import com.senla.multithreading.taskthree.thread.Manufacturer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        RepositoryNumber repositoryNumber = new RepositoryNumberImpl(condition, lock);
        Consumer consumer = new Consumer(repositoryNumber);
        Manufacturer manufacturer = new Manufacturer(repositoryNumber);
        new Thread(manufacturer).start();
        new Thread(consumer).start();
    }
}