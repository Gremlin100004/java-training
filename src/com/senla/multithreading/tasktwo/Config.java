package com.senla.multithreading.tasktwo;

import com.senla.multithreading.tasktwo.thread.Printer;

public class Config {

    public static void run(int numberOfCycles, int sleepTime){
        Thread threadOne = new Thread(new Printer(numberOfCycles, sleepTime));
        Thread threadTwo = new Thread(new Printer(numberOfCycles, sleepTime));
        threadOne.start();
        threadTwo.start();
    }
}