package com.senla;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int min = 100;
        int max = 1000;
        int number1 = randomRange(min, max);
        int number2 = randomRange(min, max);
        int number3 = randomRange(min, max);
        System.out.println("Number1 is : " + number1);
        System.out.println("Number2 is : " + number2);
        System.out.println("Number3 is : " + number3);
        System.out.println("Difference number : " + differenceNumbers(number1, number2, number3));
    }

    public static int randomRange(int min, int max) {
        Random ran = new Random();
        int number;
        number = ran.nextInt((max - min) + 1) + min;
        return number;
    }

    public static int differenceNumbers(int number1, int number2, int number3) {
        int bigNumber = number1 * 1000 + number2;
        int difNumb;
        difNumb = bigNumber - number3;
        return difNumb;
    }
}