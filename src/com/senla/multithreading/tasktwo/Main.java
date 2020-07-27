package com.senla.multithreading.tasktwo;

import com.senla.multithreading.tasktwo.thread.ThreadOne;
import com.senla.multithreading.tasktwo.thread.ThreadTwo;

public class Main {
    public static void main(String[] args) {
        ThreadOne threadOne = new ThreadOne();
        ThreadTwo threadTwo = new ThreadTwo();
        threadOne.start();
        threadTwo.start();
        while (true){
            System.out.println(threadOne.getName());
            System.out.println(threadTwo.getName());
        }
    }
}
