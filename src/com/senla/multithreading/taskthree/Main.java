package com.senla.multithreading.taskthree;

import com.senla.multithreading.taskthree.repository.RepositoryNumber;
import com.senla.multithreading.taskthree.repository.RepositoryNumberImpl;
import com.senla.multithreading.taskthree.thread.Consumer;
import com.senla.multithreading.taskthree.thread.Manufacturer;

public class Main {
    public static void main(String[] args) {
        RepositoryNumber repositoryNumber = new RepositoryNumberImpl();
        Consumer consumer = new Consumer(repositoryNumber);
        Manufacturer manufacturer = new Manufacturer(repositoryNumber);
        new Thread(manufacturer).start();
        new Thread(consumer).start();
    }
}