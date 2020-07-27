package com.senla.multithreading.taskthree.repository;

import com.senla.multithreading.taskthree.util.Randomizer;

import java.nio.IntBuffer;
import java.util.Arrays;

public class RepositoryNumberImpl implements RepositoryNumber {
    private final Integer limitBuffer = 20;
    private IntBuffer randomNumbersBuffer = IntBuffer.wrap(new int[limitBuffer]);

    public RepositoryNumberImpl() {
    }

    @Override
    public synchronized void addRandomNumber(int randomNumber){
        if (randomNumbersBuffer.position() >= randomNumbersBuffer.limit()){
            try {
                System.out.println("Thread manufacture block");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //ToDo exception
            }
        }
        randomNumbersBuffer.put(randomNumber);
        System.out.println(Arrays.toString(randomNumbersBuffer.array()));
        notify();
    }

    @Override
    public synchronized void pullOutRandomNumber(){
        if (randomNumbersBuffer.position() == 0){
            try {
                System.out.println("Thread consumer block");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //ToDo exception
            }
        }
        int currentPosition = randomNumbersBuffer.position();
        randomNumbersBuffer.position(currentPosition - 1);
        randomNumbersBuffer.get(currentPosition -1);
        notify();
    }
}