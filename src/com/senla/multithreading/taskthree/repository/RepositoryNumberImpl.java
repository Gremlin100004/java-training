package com.senla.multithreading.taskthree.repository;

import com.senla.multithreading.taskthree.util.Randomizer;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RepositoryNumberImpl implements RepositoryNumber {
    private final Integer limitBuffer = 20;
    private IntBuffer randomNumbersBuffer = IntBuffer.wrap(new int[limitBuffer]);
    private Condition condition;
    private Lock lock;

    public RepositoryNumberImpl(Condition condition, Lock lock) {
        this.condition = condition;
        this.lock = lock;
    }

    @Override
    public void addRandomNumber(int randomNumber){
        if (randomNumbersBuffer.position() >= randomNumbersBuffer.limit()){
            try {
                lock.lock();
                System.out.println("Thread manufacture block");
                condition.await();
                lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //ToDo exception
            }
        }
        lock.lock();
        randomNumbersBuffer.put(randomNumber);
        System.out.println(Arrays.toString(randomNumbersBuffer.array()));
        condition.signal();
        lock.unlock();
    }

    @Override
    public synchronized void pullOutRandomNumber(){
        while (randomNumbersBuffer.position() == 0){
            try {
                lock.lock();
                System.out.println("Thread consumer block");
                condition.await();
                lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //ToDo exception
            }
        }
        lock.lock();
        int currentPosition = randomNumbersBuffer.position();
        System.out.println("currentPosition: " + currentPosition);
        randomNumbersBuffer.position(currentPosition - 1);
        randomNumbersBuffer.get(currentPosition -1);
        condition.signal();
        lock.unlock();
    }
}