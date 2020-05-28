package com.senla;

import com.senla.math.MathNumbers;

public class Main {

    public static void main(String[] args) {
        int min = 100;
        int max = 1000;
        MathNumbers mathNumbers = new MathNumbers(min, max);
        mathNumbers.randomRange();
        int differenceNumbers = mathNumbers.calculateDifferenceNumbers();
        for (int number : mathNumbers.getArrayNumbers()) {
            System.out.println("Number is : " + number);
        }
        System.out.println("Difference number : " + differenceNumbers);
    }
}
