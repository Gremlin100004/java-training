package com.senla.carservice;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.connection.DatabaseConnection;
import com.senla.carservice.Place;
import com.senla.carservice.exception.DaoException;
import com.senla.carservice.DateUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class PlaceDaoImpl extends AbstractDao <Place> implements PlaceDao {
    private static final String SQL_REQUEST_TO_ADD_RECORD = "INSERT INTO places VALUES (NULL, ?, ?, ?)";
    private static final String SQL_REQUEST_TO_UPDATE_RECORD = "UPDATE places SET number=?, is_busy=?, is_deleted=? " +
        "WHERE id=?";
    private static final String SQL_REQUEST_TO_UPDATE_RECORDS_IF_EXIST = "INSERT INTO places(id, number, is_busy, " +
        "is_deleted) VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = ?, number = ?, is_busy = ?, is_deleted = ?";
    private static final String SQL_REQUEST_TO_DELETE_RECORD = "UPDATE places SET is_deleted=true WHERE id=?";
    private static final String SQL_REQUEST_TO_GET_ALL_RECORDS = "SELECT * FROM places";
    private static final String SQL_REQUEST_TO_GET_NUMBER_RECORDS = "SELECT COUNT(places.id) AS number_places FROM places";
    private static final String SQL_REQUEST_TO_GET_FREE_PLACES = "SELECT DISTINCT places.id, places.number, places.is_busy, " +
        "places.is_deleted FROM places LEFT JOIN orders ON places.id = orders.place_id WHERE orders.lead_time > ? UNION " +
        "SELECT DISTINCT places.id, places.number, places.is_busy, places.is_deleted FROM places LEFT JOIN orders ON " +
        "places.id = orders.place_id WHERE orders.place_id IS NULL";

    public PlaceDaoImpl() {
    }

    @Override
    public List<Place> getFreePlaces(Date executeDate, DatabaseConnection databaseConnection) {
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_TO_GET_FREE_PLACES)) {
            statement.setString(1, DateUtil.getStringFromDate(executeDate, true));
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException ex) {
            throw new DaoException("Error request get free places");
        }
    }

    @Override
    public int getNumberPlace(DatabaseConnection databaseConnection) {
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_TO_GET_NUMBER_RECORDS)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("number_places");
            }
            return 0;
        } catch (SQLException ex) {
            throw new DaoException("Error request get number places");
        }
    }

    @Override
    protected void fillStatementCreate(PreparedStatement statement, Place place) {
        try {
            statement.setInt(1, place.getNumber());
            statement.setBoolean(2, place.getBusyStatus());
            statement.setBoolean(3, place.getDelete());
        } catch (SQLException e) {
            throw new DaoException("Error fill statement for create request");
        }
    }

    @Override
    protected void fillStatementUpdate(PreparedStatement statement, Place place) {
        try {
            statement.setInt(1, place.getNumber());
            statement.setBoolean(2, place.getBusyStatus());
            statement.setBoolean(3, place.getDelete());
            statement.setLong(4, place.getId());
        } catch (SQLException e) {
            throw new DaoException("Error fill statement for update request");
        }
    }

    @Override
    protected void fillStatementUpdateAll(PreparedStatement statement, Place place) {
        try {
            statement.setLong(1, place.getId());
            statement.setInt(2, place.getNumber());
            statement.setBoolean(3, place.getBusyStatus());
            statement.setBoolean(4, place.getDelete());
            statement.setLong(5, place.getId());
            statement.setInt(6, place.getNumber());
            statement.setBoolean(7, place.getBusyStatus());
            statement.setBoolean(8, place.getDelete());
        } catch (SQLException e) {
            throw new DaoException("Error fill statement for update request");
        }
    }

    @Override
    protected void fillStatementDelete(PreparedStatement statement, Place place) {
        try {
            statement.setLong(1, place.getId());
        } catch (SQLException e) {
            throw new DaoException("Error fill statement for create request");
        }
    }

    @Override
    protected String getCreateRequest() {
        return SQL_REQUEST_TO_ADD_RECORD;
    }

    @Override
    protected String getReadAllRequest() {
        return SQL_REQUEST_TO_GET_ALL_RECORDS;
    }

    @Override
    protected String getUpdateRequest() {
        return SQL_REQUEST_TO_UPDATE_RECORD;
    }

    @Override
    protected String getUpdateAllRecordsRequest() {
        return SQL_REQUEST_TO_UPDATE_RECORDS_IF_EXIST;
    }

    @Override
    protected String getDeleteRequest() {
        return SQL_REQUEST_TO_DELETE_RECORD;
    }

    @Override
    protected List<Place> parseResultSet(ResultSet resultSet) {
        try {
            List<Place> places = new ArrayList<>();
            while (resultSet.next()) {
                Place place = new Place();
                place.setId(resultSet.getLong("id"));
                place.setNumber(resultSet.getInt("number"));
                place.setDelete(resultSet.getBoolean("is_deleted"));
                place.setBusyStatus(resultSet.getBoolean("is_busy"));
                places.add(place);
            }
            return places;
        } catch (SQLException ex) {
            throw new DaoException("Error request get records places");
        }
    }
}