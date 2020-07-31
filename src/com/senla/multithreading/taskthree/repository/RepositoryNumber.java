package com.senla.multithreading.taskthree.repository;

// перевод: число репозитория
public interface RepositoryNumber {
    void addRandomNumber(int randomNumber);

    int pullOutRandomNumber();
}