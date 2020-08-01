package com.senla.multithreading.task2.repository;

public class FlagRepository {
    private volatile Boolean flag = true;

    public synchronized Boolean getFlag() {
        return flag;
    }

    public synchronized void changeFlag() {
        flag = !flag;
    }
}