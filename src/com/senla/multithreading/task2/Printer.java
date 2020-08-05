package com.senla.multithreading.task2;

public class Printer {

    public synchronized void printName(String name) {
        try {
            notify();
            System.out.println("My name is " + name);
            wait(100);
        } catch (InterruptedException exception) {
            System.out.println(exception.getMessage());
        }
    }
}