package com.senla.multithreading.taskthree.util;

import java.util.Random;

public class Randomizer {
    public static int getRandomNumber(int min, int max) {
        int numbersDifference = max - min;
        // лучше вынести в константу и переиспользовать
        Random random = new Random();
        return random.nextInt(numbersDifference + 1) + min;
    }
}