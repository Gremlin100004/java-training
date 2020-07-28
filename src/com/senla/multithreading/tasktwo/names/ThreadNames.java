package com.senla.multithreading.tasktwo.names;

import com.senla.multithreading.tasktwo.names.thread.ThreadPrinter;

public class ThreadNames {

    public static void run(int repetitionsNumber, int sleepTime){
        Thread threadOne = new Thread(new ThreadPrinter(repetitionsNumber, sleepTime));
        Thread threadTwo = new Thread(new ThreadPrinter(repetitionsNumber, sleepTime));
        threadOne.start();
        threadTwo.start();
    }
}
