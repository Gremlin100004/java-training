package com.senla.carservice.hibernatedao;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {

    void saveRecord(T object, Session session);

    List<T> getAllRecords(Session session, Class<T> type);

    void updateRecord(T object, Session session);

    void updateAllRecords(List<T> objects, Session session);

    void deleteRecord(PK id, Session session);
}