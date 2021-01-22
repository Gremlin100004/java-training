package com.senla.socialnetwork.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {

    T saveRecord(T object);

    T findById(PK id);

    List<T> getAllRecords(int firstResult, int maxResults);

    void updateRecord(T object);

    void deleteRecord(T entity);

}
