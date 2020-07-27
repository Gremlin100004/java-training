package com.senla.multithreading.tasktwo;

import com.senla.multithreading.tasktwo.thread.ThreadOne;
import com.senla.multithreading.tasktwo.thread.ThreadTwo;

public class Main {
    public static void main(String[] args) {
        Integer repetitionsNumber = 10;
        Integer sleepTime = 500;
        ThreadOne threadOne = new ThreadOne(repetitionsNumber, sleepTime);
        ThreadTwo threadTwo = new ThreadTwo(repetitionsNumber, sleepTime);
        new Thread(threadOne).start();
        new Thread(threadTwo).start();
    }
}