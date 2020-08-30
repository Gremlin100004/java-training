package com.senla.carservice;

import com.senla.carservice.connection.DatabaseConnection;

import java.util.Date;
import java.util.List;

public interface PlaceDao extends GenericDao <Place> {
    List<Place> getFreePlaces(Date startDayDate, DatabaseConnection databaseConnection);

    int getNumberPlaces(DatabaseConnection databaseConnection);

    Place getPlaceById(Long index, DatabaseConnection databaseConnection);
}