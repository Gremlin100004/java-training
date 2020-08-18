package com.senla.carservice.dao;

import java.util.List;

public interface GenericDao <T> {

    void createRecord(T object);

    List<T> getAllRecords();

    void updateRecord(T object);

    void updateAllRecords(List<T> objects);

    void deleteRecord(T object);
}
