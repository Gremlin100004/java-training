package com.senla.multithreading.taskthree;

// просьба переделать задачу без использования канкарент (используй ключевые слова
// синхронайзд, вейт, нотифай
// постарайся не усложнять задание
public class Main {
    public static void main(String[] args) {
        // перевод: буфер лимита
        int limitBuffer = 20;
        int numberOfCycles = 100;
        int min = 0;
        int max = 100;
        Config.run(limitBuffer, numberOfCycles, min, max);
    }
}