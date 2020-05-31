package com.senla.mathoperation.math;

import java.util.Random;

public class Math {
    private int[] arrayNumbers;
    private int min;
    private int max;
    private final int numberDigits = 3;

    public Math(int min, int max) {
        this.min = min;
        this.max = max;
        arrayNumbers = new int[numberDigits];
    }

    public int[] getArrayNumbers() {
        int[] transmittedArrayNumbers = new int[this.arrayNumbers.length];
        System.arraycopy(arrayNumbers, 0, transmittedArrayNumbers, 0, this.arrayNumbers.length);
        return transmittedArrayNumbers;
    }

    public void generateRandomRange() {
        Random ran = new Random();
        for (int i = 0; i < this.arrayNumbers.length; i++) {
            this.arrayNumbers[i] = ran.nextInt((this.max - this.min) + 1) + this.min;
        }
    }

    public int calculateDifferenceNumbers() {
        int bigNumber = this.arrayNumbers[0] * 1000 + this.arrayNumbers[1];
        return bigNumber - this.arrayNumbers[2];
    }
}
