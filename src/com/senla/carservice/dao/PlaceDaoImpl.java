package com.senla.carservice.dao;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.ConstructorDependency;
import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.domain.Master;
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

@Singleton
public class PlaceDaoImpl extends AbstractDao implements PlaceDao {
    private static final String SQL_REQUEST_TO_ADD_RECORD = "INSERT INTO places VALUES (NULL, ?, ?, ?)";
    private static final String SQL_REQUEST_TO_UPDATE_RECORD = "UPDATE places SET number=?, busy_status=?, delete_status=? " +
         "WHERE id=?";
    private static final String SQL_REQUEST_TO_DELETE_RECORD = "UPDATE places SET delete_status=true WHERE id=?";
    private static final String SQL_REQUEST_TO_GET_ALL_RECORDS = "SELECT * FROM places";
    private static final String SQL_REQUEST_TO_GET_NUMBER_RECORDS = "SELECT COUNT(places.id) AS number_places FROM places";
    private static final String SQL_REQUEST_TO_GET_FREE_PLACES = "SELECT DISTINCT places.id, places.number, places.busy_status, " +
         "places.delete_status FROM places LEFT JOIN orders ON places.id = orders.place_id WHERE orders.lead_time > ?";

    @ConstructorDependency
    public PlaceDaoImpl(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

    public PlaceDaoImpl() {
    }

    @Override
    public List<Place> getFreePlaces(Date executeDate) {
        //ToDO get free place
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(SQL_REQUEST_TO_GET_FREE_PLACES)) {
            statement.setString(1, DateUtil.getStringFromDate(executeDate, true));
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException ex) {
            throw new BusinessException("Error request get number free masters");
        }
    }

    @Override
    public int getNumberPlace(){
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(SQL_REQUEST_TO_GET_NUMBER_RECORDS)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("number_places");
        } catch (SQLException ex) {
            throw new BusinessException("Error request get number places");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<Place> parseResultSet(ResultSet resultSet) {
        try {
            List<Place> places = new ArrayList<>();
            while (resultSet.next()) {
                Place place = new Place();
                place.setId(resultSet.getLong("id"));
                place.setNumber(resultSet.getInt("number"));
                place.setDelete(resultSet.getBoolean("delete_status"));
                place.setBusyStatus(resultSet.getBoolean("busy_status"));
                places.add(place);
            }
            return places;
        } catch (SQLException ex) {
            throw new BusinessException("Error request get records places");
        }
    }

    @Override
    protected <T> void fillStatementCreate(PreparedStatement statement, T object) {
        Place place = (Place) object;
        try {
            statement.setInt(1, place.getNumber());
            statement.setBoolean(2, place.getBusyStatus());
            statement.setBoolean(3, place.getDelete());
        } catch (SQLException e) {
            throw new BusinessException("Error fill statement for create request");
        }
    }

    @Override
    protected <T> void fillStatementUpdate(PreparedStatement statement, T object) {
        Place place = (Place) object;
        try {
            statement.setInt(1, place.getNumber());
            statement.setBoolean(2, place.getBusyStatus());
            statement.setBoolean(3, place.getDelete());
            statement.setLong(4, place.getId());
        } catch (SQLException e) {
            throw new BusinessException("Error fill statement for update request");
        }
    }

    @Override
    protected <T> void fillStatementDelete(PreparedStatement statement, T object) {
        Place place = (Place) object;
        try {
            statement.setLong(1, place.getId());
        } catch (SQLException e) {
            throw new BusinessException("Error fill statement for create request");
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
    protected String getDeleteRequest() {
        return SQL_REQUEST_TO_DELETE_RECORD;
    }
}