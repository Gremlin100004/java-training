package com.senla.multithreading.taskthree;

import com.senla.multithreading.taskthree.repository.RepositoryNumber;
import com.senla.multithreading.taskthree.repository.RepositoryNumberImpl;
import com.senla.multithreading.taskthree.thread.Consumer;
import com.senla.multithreading.taskthree.thread.Manufacturer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// пожалуйста, измени нейминг, у Конфиг класса не может быть метода ран, он может хранить
// и отдавать конфиги, но никак не может что-то запускать
// в принципе, демонстрация программы в мейн для этих задач норм
public class Config {
    public static void run(int limitBuffer, int numberOfCycles, int min, int max) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        RepositoryNumber repositoryNumber = new RepositoryNumberImpl(condition, lock, limitBuffer);
        Consumer consumer = new Consumer(repositoryNumber, numberOfCycles);
        // общепринятое имя для производителья - Продьюсер
        Manufacturer manufacturer = new Manufacturer(repositoryNumber, numberOfCycles, min, max);
        new Thread(manufacturer).start();
        new Thread(consumer).start();
    }
}