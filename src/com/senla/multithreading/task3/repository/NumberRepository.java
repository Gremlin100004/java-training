package com.senla.multithreading.task3.repository;

import com.senla.multithreading.common.exception.ThreadException;

import java.nio.IntBuffer;

public class NumberRepository {
    // аррей лист прекрасно бы справился с ролью буфера
    private final IntBuffer randomNumbersBuffer;

    public NumberRepository(int bufferLimit) {
        this.randomNumbersBuffer = IntBuffer.wrap(new int[bufferLimit]);
    }

    public synchronized void addRandomNumber(int randomNumber) {
        if (randomNumbersBuffer.position() >= randomNumbersBuffer.limit()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new ThreadException("Error wait thread");
            }
        }
        randomNumbersBuffer.put(randomNumber);
        notify();
    }

    public synchronized int pullOutRandomNumber() {
        while (randomNumbersBuffer.position() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new ThreadException("Error wait thread");
            }
        }
        int currentPosition = randomNumbersBuffer.position();
        randomNumbersBuffer.position(currentPosition - 1);
        int randomNumber = randomNumbersBuffer.get(currentPosition - 1);
        notify();
        return randomNumber;
    }
}