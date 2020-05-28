package com.senla.math;

import java.util.Random;

public class MathNumbers {
    private int[] arrayNumbers;
    private int min;
    private int max;
    final int numberDigits = 3;

    public MathNumbers(int min, int max) {
        this.min = min;
        this.max = max;
        arrayNumbers = new int[numberDigits];
    }

    public int[] getArrayNumbers() {
        return arrayNumbers;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void setArrayNumbers(int[] arrayNumbers) {
        this.arrayNumbers = arrayNumbers;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void randomRange() {
        Random ran = new Random();
        for (int i = 0; i < numberDigits; i++) {
            this.arrayNumbers[i] = ran.nextInt((this.max - this.min) + 1) + this.min;
        }
    }

    public int calculateDifferenceNumbers() {
        int bigNumber = this.arrayNumbers[0] * 1000 + this.arrayNumbers[1];
        return bigNumber - this.arrayNumbers[2];
    }
}
