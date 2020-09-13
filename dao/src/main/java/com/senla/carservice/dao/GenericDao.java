package com.senla.carservice.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {

    void saveRecord(T object);

    T findById(Class<T> type, PK id);

    List<T> getAllRecords(Class<T> type);

    void updateRecord(T object);

    void updateAllRecords(List<T> objects);

    void deleteRecord(PK id);
}