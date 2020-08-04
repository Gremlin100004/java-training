package com.senla.multithreading.task2;

public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Runnable task = () -> {
            int count = 5;
            while (count-- > 0) {
                printer.printName(Thread.currentThread().getName());
            }
        };
        Thread first = new Thread(task, "First");
        Thread second = new Thread(task, "Second");
        first.start();
        second.start();
    }
}