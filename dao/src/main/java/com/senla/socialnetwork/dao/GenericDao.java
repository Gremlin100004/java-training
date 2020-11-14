package com.senla.socialnetwork.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {

    void setType(Class<T> type);

    T saveRecord(T object);

    T findById(PK id);

    List<T> getAllRecords(int firstResult, int maxResults);

    void updateRecord(T object);

    void deleteRecord(PK id);

}
