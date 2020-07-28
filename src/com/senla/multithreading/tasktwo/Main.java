package com.senla.multithreading.tasktwo;

import com.senla.multithreading.tasktwo.names.ThreadNames;

public class Main {

    public static void main(String[] args) {
        int repetitionsNumber = 10;
        int sleepTime = 500;
        ThreadNames.run(repetitionsNumber, sleepTime);
    }
}