package com.senla.carservice.hibernatedao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {

     void saveRecord(T object);

     List<T> getAllRecords(Class<T> type);

     void updateRecord(T object);

     void updateAllRecords(List<T> objects);

     void deleteRecord(PK id);

     SessionFactory getSessionFactory();
}