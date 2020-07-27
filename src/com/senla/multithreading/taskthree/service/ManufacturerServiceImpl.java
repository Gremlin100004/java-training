package com.senla.multithreading.taskthree.service;

import com.senla.multithreading.taskthree.repository.RepositoryNumber;
import com.senla.multithreading.taskthree.util.Randomizer;

public class ManufacturerServiceImpl implements ManufacturerService {
    private RepositoryNumber repositoryNumber;

    public ManufacturerServiceImpl(RepositoryNumber repositoryNumber) {
        this.repositoryNumber = repositoryNumber;
    }

    @Override
    public void produceRandomNumber(){
        int randomNumber = Randomizer.getRandomNumber();
        repositoryNumber.addRandomNumber(randomNumber);
    }
}