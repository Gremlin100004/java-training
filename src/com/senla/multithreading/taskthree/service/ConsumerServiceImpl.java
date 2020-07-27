package com.senla.multithreading.taskthree.service;

import com.senla.multithreading.taskthree.repository.RepositoryNumber;

public class ConsumerServiceImpl implements ConsumerService {
    private RepositoryNumber repositoryNumber;

    public ConsumerServiceImpl(final RepositoryNumber repositoryNumber) {
        this.repositoryNumber = repositoryNumber;
    }

    @Override
    public void consumeRandomNumber(){
        repositoryNumber.pullOutRandomNumber();
    }
}