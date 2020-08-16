package com.senla.carservice.dao;

import java.util.List;

public interface Dao<T> {

    public void createRecord(T object);

    public List<T> getAllRecords();

    public void updateRecord(T object);

    public void updateAllRecords(T object);

    public void deleteRecord(T object);
}