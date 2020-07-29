package com.senla.multithreading.taskthree.repository;

public interface RepositoryNumber {
    void addRandomNumber(int randomNumber);

    int pullOutRandomNumber();
}