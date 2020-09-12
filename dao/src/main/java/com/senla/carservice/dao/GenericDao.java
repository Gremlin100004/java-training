package com.senla.carservice.dao;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {

    Serializable saveRecord(T object);

    T getRecordById(Class<T> type, PK id);

    List<T> getAllRecords(Class<T> type);

    void updateRecord(T object);

    void updateAllRecords(List<T> objects);

    void deleteRecord(PK id);

    Session getSession();
}