package com.senla.multithreading.taskthree;

public class Main {
    public static void main(String[] args) {
        int limitBuffer = 20;
        int numberOfCycles = 100;
        int min = 0;
        int max = 100;
        Config.run(limitBuffer, numberOfCycles, min, max);
    }
}