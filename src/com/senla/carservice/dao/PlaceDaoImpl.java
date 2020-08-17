package com.senla.carservice.dao;

import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.DateUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaceDaoImpl implements PlaceDao {
    @ConfigProperty
    private DatabaseConnection databaseConnection;
    private static final String SQL_REQUEST_TO_ADD_RECORD = "INSERT INTO places VALUES (NULL, ?, ?)";
    private static final String SQL_REQUEST_TO_UPDATE_RECORD = "UPDATE places SET name=?, busy_status=?, delete_status=?" +
                                                               " WHERE id=?";
    private static final String SQL_REQUEST_TO_DELETE_RECORD = "UPDATE places SET delete_status=true WHERE id=?";
    private static final String SQL_REQUEST_TO_GET_ALL_RECORDS = "SELECT * FROM places";
    private static final String SQL_REQUEST_TO_GET_FREE_PLACES = "SELECT DISTINCT places.id, places.number" +
                                                                 "FROM places " +
                                                                 "JOIN orders " +
                                                                 "ON places.id = orders.place_id " +
                                                                 "WHERE orders.lead_time > ";

    @Override
    public List<Place> getPlaces() {
        return getPlacesFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS);
    }

    @Override
    public List<Place> getFreePlaces(Date startDayDate) {
        return getPlacesFromDatabase(SQL_REQUEST_TO_GET_FREE_PLACES + DateUtil.getStringFromDate(startDayDate, true));
    }

    @Override
    public void addPlace(Place place) {
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_TO_ADD_RECORD)) {
            statement.setInt(1, place.getNumber());
            statement.setBoolean(2, place.getBusyStatus());
            statement.execute();
        } catch (SQLException ex) {
            throw new BusinessException("Error request add record places");
        }
    }

    @Override
    public void updatePlace(Place place) {
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_TO_UPDATE_RECORD)) {
            statement.setInt(1, place.getNumber());
            statement.setBoolean(2, place.getBusyStatus());
            statement.setBoolean(3, place.getDelete());
            statement.setLong(4, place.getId());
            statement.execute();
        } catch (SQLException ex) {
            throw new BusinessException("Error request update record places");
        }
    }

    @Override
    public void deletePlace(Place place) {
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_TO_DELETE_RECORD)) {
            statement.setLong(1, place.getId());
            statement.execute();
        } catch (SQLException ex) {
            throw new BusinessException("Error request delete record places");
        }
    }

    @Override
    public void updateListPlace(List<Place> places) {
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_TO_UPDATE_RECORD)) {
            for (Place place : places) {
                statement.setInt(1, place.getNumber());
                statement.setBoolean(2, place.getBusyStatus());
                statement.setBoolean(3, place.getDelete());
                statement.setLong(4, place.getId());
                statement.execute();
            }
        } catch (SQLException ex) {
            throw new BusinessException("Error request update all records places");
        }
    }

    private List<Place> getPlacesFromDatabase(String sqlRequest) {
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            List<Place> places = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while (resultSet.next()) {
                Place place = new Place();
                place.setId(resultSet.getLong("id"));
                place.setNumber(resultSet.getInt("number"));
                place.setBusyStatus(resultSet.getBoolean("busy_status"));
                places.add(place);
            }
            return places;

        } catch (SQLException ex) {
            throw new BusinessException("Error request get record masters");
        }
    }
}