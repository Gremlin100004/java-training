package com.senla.carservice.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {

    void setType(Class<T> type);

    void saveRecord(T object);

    T findById(PK id);

    List<T> getAllRecords();

    void updateRecord(T object);

    void updateAllRecords(List<T> objects);

    void deleteRecord(PK id);
}