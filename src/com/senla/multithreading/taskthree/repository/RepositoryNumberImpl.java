package com.senla.multithreading.taskthree.repository;

import com.senla.multithreading.taskthree.exception.ThreadException;

import java.nio.IntBuffer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RepositoryNumberImpl implements RepositoryNumber {
    private final IntBuffer randomNumbersBuffer;
    private final Condition condition;
    private final Lock lock;

    public RepositoryNumberImpl(Condition condition, Lock lock, int limitBuffer) {
        this.condition = condition;
        this.lock = lock;
        this.randomNumbersBuffer = IntBuffer.wrap(new int[limitBuffer]);
    }

    @Override
    public void addRandomNumber(int randomNumber) {
        if (randomNumbersBuffer.position() >= randomNumbersBuffer.limit()) {
            try {
                lock.lock();
                condition.await();
                lock.unlock();
            } catch (InterruptedException e) {
                throw new ThreadException("Error wait thread");
            }
        }
        lock.lock();
        randomNumbersBuffer.put(randomNumber);
        condition.signal();
        lock.unlock();
    }

    @Override
    public synchronized int pullOutRandomNumber() {
        while (randomNumbersBuffer.position() == 0) {
            try {
                lock.lock();
                condition.await();
                lock.unlock();
            } catch (InterruptedException e) {
                throw new ThreadException("Error wait thread");
            }
        }
        lock.lock();
        int currentPosition = randomNumbersBuffer.position();
        randomNumbersBuffer.position(currentPosition - 1);
        int randomNumber = randomNumbersBuffer.get(currentPosition - 1);
        condition.signal();
        lock.unlock();
        return randomNumber;
    }
}