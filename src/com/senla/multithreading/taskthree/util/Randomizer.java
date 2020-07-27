package com.senla.multithreading.taskthree.util;

import java.util.Random;

public class Randomizer {
    private static int min = 0;
    private static int max = 100;

    public static void setMinMax(int min, int max) {
        Randomizer.min = min;
        Randomizer.max = max;
    }

    public static int getRandomNumber(){
        int diff = max - min;
        Random random = new Random();
        return random.nextInt(diff + 1) + min;
    }
}