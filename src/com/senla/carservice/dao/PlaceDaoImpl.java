package com.senla.carservice.dao;

import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaceDaoImpl implements PlaceDao {
    @ConfigProperty
    private DatabaseConnection databaseConnection;
    private static final String SQL_REQUEST_TO_ADD_RECORD = "INSERT INTO masters VALUES (NULL, ?)";
    private static final String SQL_REQUEST_TO_UPDATE_RECORD = "UPDATE masters SET name=? WHERE id=?";
    private static final String SQL_REQUEST_TO_DELETE_RECORD = "DELETE FROM masters WHERE id=?";
    private static final String SQL_REQUEST_TO_GET_ALL_RECORDS = "SELECT * FROM masters";
    private static final String SQL_REQUEST_TO_GET_FREE_PLACES = "SELECT DISTINCT masters.id, masters.name " +
                                                                  "FROM masters " +
                                                                  "INNER JOIN orders_masters " +
                                                                  "ON masters.id = orders_masters.master_id " +
                                                                  "LEFT JOIN orders " +
                                                                  "ON orders_masters.order_id = orders.id " +
                                                                  "WHERE orders.lead_time > ";
    @Override
    public List<Place> getPlaces() {
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            List<Place> places = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SQL_REQUEST_TO_GET_ALL_RECORDS);
            while (resultSet.next()) {
                Place place = new Place();
                place.setId(resultSet.getLong("id"));
                place.setNumber(resultSet.getInt("number"));
                places.add(place);
            }
            return places;

        } catch (SQLException ex) {
            throw new BusinessException("Error request get record masters");
        }
    }

    @Override
    public List<Place> getFreePlaces(final Date startDayDate) {
        return null;
    }

    @Override
    public void addPlace(final Place place) {
    }

    @Override
    public void updatePlace(final Place place) {
    }

    @Override
    public void deletePlace(final Place place) {
    }

    @Override
    public void updateListPlace(final List<Place> places) {
    }
}