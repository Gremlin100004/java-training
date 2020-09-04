package com.senla.carservice.hibernatedao;

import com.senla.carservice.domain.Place;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.hibernatedao.exception.DaoException;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

@Singleton
public class PlaceDaoImpl extends AbstractDao<Place> implements PlaceDao {

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
    private static final String SQL_REQUEST_GET_ORDER_BY_ID = "SELECT DISTINCT id FROM places WHERE id=?";

    public PlaceDaoImpl() {
    }

    @Override
    public List<Place> getFreePlaces(Date executeDate, Session session) {
        LOGGER.debug("Method getFreePlaces");
        LOGGER.trace("Parameter executeDate: {}", executeDate);
        LOGGER.trace("Parameter databaseConnection: {}",  session);
        try {
//            SQL_REQUEST_TO_GET_FREE_PLACES
//            statement.setString(1, DateUtil.getStringFromDate(executeDate, true));
//            ResultSet resultSet = statement.executeQuery();
//            return parseResultSet(resultSet);
            return null;
        } catch (Exception ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get free places");
        }
    }

    @Override
    public int getNumberPlaces(Session  session) {
        LOGGER.debug("Method getNumberPlaces");
        LOGGER.debug("Parameter databaseConnection: {}",  session);
        try {
//            SQL_REQUEST_TO_GET_NUMBER_RECORDS
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return resultSet.getInt("number_places");
//            }
            return 0;
        } catch (Exception ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get number places");
        }
    }

    @Override
    public Place getPlaceById(Long index, Session  session) {
        LOGGER.debug("Method getPlaceById");
        LOGGER.debug("Parameter index: {}", index);
        LOGGER.debug("Parameter databaseConnection: {}",  session);
        try {
//            statement.setLong(1, index);
//            SQL_REQUEST_GET_ORDER_BY_ID
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return parseResultSet(resultSet).get(0);
//            }
            return null;
        } catch (Exception ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get place by id");
        }
    }

//    @Override
//    protected void fillStatementCreate(PreparedStatement statement, Place place) {
//        LOGGER.debug("Method fillStatementCreate");
//        LOGGER.debug("Parameter statement: {}", statement);
//        LOGGER.debug("Parameter place: {}", place);
//        try {
//            statement.setInt(1, place.getNumber());
//            statement.setBoolean(2, place.getIsBusy());
//            statement.setBoolean(3, place.getDelete());
//        } catch (SQLException ex) {
//            LOGGER.error(String.valueOf(ex));
//            throw new DaoException("Error fill statement for create request");
//        }
//    }

//    @Override
//    protected void fillStatementUpdate(PreparedStatement statement, Place place) {
//        LOGGER.debug("Method fillStatementUpdate");
//        LOGGER.debug("Parameter statement: {}", statement);
//        LOGGER.debug("Parameter place: {}", place);
//        try {
//            statement.setInt(1, place.getNumber());
//            statement.setBoolean(2, place.getIsBusy());
//            statement.setBoolean(3, place.getDelete());
//            statement.setLong(4, place.getId());
//        } catch (SQLException ex) {
//            LOGGER.error(String.valueOf(ex));
//            throw new DaoException("Error fill statement for update request");
//        }
//    }

//    @Override
//    protected void fillStatementUpdateAll(PreparedStatement statement, Place place) {
//        LOGGER.debug("Method fillStatementUpdateAll");
//        LOGGER.debug("Parameter statement: {}", statement);
//        LOGGER.debug("Parameter place: {}", place);
//        try {
//            statement.setLong(1, place.getId());
//            statement.setInt(2, place.getNumber());
//            statement.setBoolean(3, place.getIsBusy());
//            statement.setBoolean(4, place.getDelete());
//            statement.setLong(5, place.getId());
//            statement.setInt(6, place.getNumber());
//            statement.setBoolean(7, place.getIsBusy());
//            statement.setBoolean(8, place.getDelete());
//        } catch (SQLException ex) {
//            LOGGER.error(String.valueOf(ex));
//            throw new DaoException("Error fill statement for update request");
//        }
//    }

//    @Override
//    protected void fillStatementDelete(PreparedStatement statement, Place place) {
//        LOGGER.debug("Method fillStatementDelete");
//        LOGGER.debug("Parameter statement: {}", statement);
//        LOGGER.debug("Parameter place: {}", place);
//        try {
//            statement.setLong(1, place.getId());
//        } catch (SQLException ex) {
//            LOGGER.error(String.valueOf(ex));
//            throw new DaoException("Error fill statement for create request");
//        }
//    }

//    @Override
//    protected String getCreateRequest() {
//        return SQL_REQUEST_TO_ADD_RECORD;
//    }

//    @Override
//    protected String getReadAllRequest() {
//        return SQL_REQUEST_TO_GET_ALL_RECORDS;
//    }

//    @Override
//    protected String getUpdateRequest() {
//        return SQL_REQUEST_TO_UPDATE_RECORD;
//    }

//    @Override
//    protected String getUpdateAllRecordsRequest() {
//        return SQL_REQUEST_TO_UPDATE_RECORDS_IF_EXIST;
//    }

//    @Override
//    protected String getDeleteRequest() {
//        return SQL_REQUEST_TO_DELETE_RECORD;
//    }

//    @Override
//    protected List<Place> parseResultSet(ResultSet resultSet) {
//        LOGGER.debug("Method parseResultSet");
//        LOGGER.trace("Parameter resultSet: {}", resultSet);
//        try {
//            List<Place> places = new ArrayList<>();
//            while (resultSet.next()) {
//                Place place = new Place();
//                place.setId(resultSet.getLong("id"));
//                place.setNumber(resultSet.getInt("number"));
//                place.setDelete(resultSet.getBoolean("is_deleted"));
//                place.setIsBusy(resultSet.getBoolean("is_busy"));
//                places.add(place);
//            }
//            return places;
//        } catch (SQLException ex) {
//            LOGGER.error(String.valueOf(ex));
//            throw new DaoException("Error request get records places");
//        }
//    }
}