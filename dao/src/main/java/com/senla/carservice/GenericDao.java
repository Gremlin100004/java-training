package com.senla.carservice;

import com.senla.carservice.connection.DatabaseConnection;

import java.util.List;

public interface GenericDao <T> {

     void createRecord(T object, DatabaseConnection databaseConnection);

     List<T> getAllRecords(DatabaseConnection databaseConnection);

     void updateRecord(T object, DatabaseConnection databaseConnection);

     void updateAllRecords(List<T> objects, DatabaseConnection databaseConnection);

     void deleteRecord(T object, DatabaseConnection databaseConnection);
}