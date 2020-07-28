package com.senla.multithreading.taskfour;

public class Main {
    public static void main(String[] args) {
        Integer repetitionsNumber = 10;
        Integer sleepTime = 500;
        ServiceStream serviceStream = new ServiceStream(repetitionsNumber, sleepTime);
        new Thread(serviceStream).start();
    }
}