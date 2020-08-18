package com.senla.carservice.dao;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.ConstructorDependency;
import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.DateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class PlaceDaoImpl extends AbstractDao implements PlaceDao {
    private static final String SQL_REQUEST_TO_ADD_RECORD = "INSERT INTO places VALUES (NULL";
    private static final String END_REQUEST_TO_ADD_RECORD = ")";
    private static final String SEPARATOR = ", ";
    private static final String SQL_REQUEST_TO_UPDATE_RECORD = "UPDATE places SET name=";
    private static final String FIELD_BUSY_STATUS = " busy_status=";
    private static final String FIELD_DELETE_STATUS = " delete_status=";
    private static final String PRIMARY_KEY_FIELD = " WHERE id=";
    private static final String SQL_REQUEST_TO_DELETE_RECORD = "UPDATE places SET delete_status=true WHERE id=";
    private static final String SQL_REQUEST_TO_GET_ALL_RECORDS = "SELECT * FROM places";
    private static final String SQL_REQUEST_TO_GET_FREE_PLACES = "SELECT DISTINCT places.id, places.number" +
         "FROM places JOIN orders ON places.id = orders.place_id WHERE orders.lead_time > ";

    @ConstructorDependency
    public PlaceDaoImpl(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

    public PlaceDaoImpl() {
    }

    @Override
    public List<Place> getFreePlaces(Date executeDate) {
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_REQUEST_TO_GET_FREE_PLACES +
                                                         DateUtil.getStringFromDate(executeDate, true));
            return parseResultSet(resultSet);
        } catch (SQLException ex) {
            throw new BusinessException("Error request get record masters");
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
    protected String getCreateRequest(Object object) {
        Place place = (Place) object;
        return SQL_REQUEST_TO_ADD_RECORD + place.getNumber() + SEPARATOR + place.getBusyStatus() + SEPARATOR
               + place.getDelete() + END_REQUEST_TO_ADD_RECORD;
    }

    @Override
    protected String getReadAllRequest() {
        return SQL_REQUEST_TO_GET_ALL_RECORDS;
    }

    @Override
    protected String getUpdateRequest(Object object) {
        Place place = (Place) object;
        return SQL_REQUEST_TO_UPDATE_RECORD + place.getNumber() + FIELD_DELETE_STATUS + place.getDelete() +
               FIELD_BUSY_STATUS + place.getDelete() + PRIMARY_KEY_FIELD + place.getId();
    }

    @Override
    protected String getDeleteRequest(Object object) {
        Place place = (Place) object;
        return SQL_REQUEST_TO_DELETE_RECORD + place.getId();
    }
}