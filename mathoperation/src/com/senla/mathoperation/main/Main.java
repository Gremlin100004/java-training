package com.senla.mathoperation.main;

import com.senla.mathoperation.math.Math;

public class Main {

    public static void main(String[] args) {
        int min = 100;
        int max = 1000;
        Math mathNumbers = new Math(min, max);
        mathNumbers.generateRandomRange();
        int differenceNumbers = mathNumbers.calculateDifferenceNumbers();
        for (int number : mathNumbers.getArrayNumbers()) {
            System.out.println("Number is : " + number);
        }
        System.out.println("Difference number : " + differenceNumbers);
    }
}
