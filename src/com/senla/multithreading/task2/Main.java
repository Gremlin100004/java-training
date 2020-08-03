package com.senla.multithreading.task2;

import com.senla.multithreading.task2.repository.FlagRepository;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        int numberOfCycles = 10;
        FlagRepository resource = new FlagRepository();
        Thread firstThread = new Thread(() -> {
            int i = 0;
            while (i < numberOfCycles) {
                if (resource.getFlag()) {
                    System.out.println(Thread.currentThread().getName());
                    resource.changeFlag();
                    i++;
                }
            }
        });
        firstThread.setName("First thread");
        firstThread.start();
        // хочу посмотреть, как бы ты добавил третий и четвертый потоки, потому что булен
        // варианты флага, похоже, тут заканчиваются
        Thread secondThread = new Thread(() -> {
            int i = 0;
            while (i < numberOfCycles) {
                if (!resource.getFlag()) {
                    System.out.println(Thread.currentThread().getName());
                    resource.changeFlag();
                    i++;
                }
            }
        });
        secondThread.setName("Second thread");
        secondThread.start();
    }

    // когда я говорю, что не нужно усложнять, я имею в виду что-то такое
    // (разбить на классы, причесать)
    public static void main2(String[] args) {
        // вынести в класс Принтер
        final Consumer<String> printer = new Consumer<>(){

            @Override
            // printName(String name)
            public synchronized void accept(String name) {
                try {
                    notifyAll();
                    System.out.println("My name is " + name);
                    wait();
                } catch (InterruptedException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        };

        Runnable task = () -> {
            int count = 5;
            while (count-- > 0) {
                printer.accept(Thread.currentThread().getName());
            }
        };

        Thread first = new Thread(task, "First");
        Thread second = new Thread(task, "Second");

        first.start();
        second.start();
        // придумать остановку программы
        // можно еще проще, вообще без классов, только одиними методами в классе Мейн,
        // но с классами более ООП получается
    }
}