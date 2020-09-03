package hibernatedao;

import com.senla.carservice.DateUtil;
import com.senla.carservice.Master;
import com.senla.carservice.container.annotation.Singleton;
import hibernatedao.exception.DaoException;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class MasterDaoImpl extends AbstractDao<Master> implements MasterDao {

    private static final String SQL_REQUEST_TO_ADD_RECORD = "INSERT INTO masters VALUES (NULL, ?, ?, ?)";
    private static final String SQL_REQUEST_TO_UPDATE_RECORD = "UPDATE masters SET name=?, number_orders=?, is_deleted=? WHERE id=?";
    private static final String SQL_REQUEST_TO_UPDATE_RECORDS_IF_EXIST = "INSERT INTO masters(id, name, number_orders, " +
        "is_deleted) VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = ?, name = ?, number_orders = ?, is_deleted = ?";
    private static final String SQL_REQUEST_TO_GET_NUMBER_RECORDS = "SELECT COUNT(masters.id) AS number_masters FROM masters";
    private static final String SQL_REQUEST_TO_DELETE_RECORD = "UPDATE masters SET is_deleted=true WHERE id=?";
    private static final String SQL_REQUEST_TO_GET_ALL_RECORDS = "SELECT DISTINCT id, name, number_orders, is_deleted " +
        "FROM masters";
    private static final String SQL_REQUEST_TO_ALL_RECORDS_BY_ALPHABET = "SELECT DISTINCT id, name, number_orders, " +
        "is_deleted FROM masters ORDER BY masters.name";
    private static final String SQL_REQUEST_TO_ALL_RECORDS_BY_BUSY = "SELECT DISTINCT id, name, number_orders, " +
        "is_deleted FROM masters ORDER BY number_orders";
    private static final String SQL_REQUEST_GET_MASTER_BY_ID = "SELECT DISTINCT id FROM masters WHERE id=?";
    private static final String SQL_REQUEST_TO_GET_FREE_MASTERS = "SELECT DISTINCT masters.id, masters.name, " +
            "masters.number_orders, masters.is_deleted FROM masters JOIN orders_masters ON masters.id = " +
            "orders_masters.master_id JOIN orders ON orders_masters.order_id = orders.id WHERE orders.lead_time > ? " +
            "UNION SELECT DISTINCT id, name, number_orders, is_deleted FROM masters WHERE number_orders = 0";

    public MasterDaoImpl() {
    }

    @Override
    public List<Master> getFreeMasters(Date date, Session session) {
        LOGGER.debug("Method getFreeMasters");
        LOGGER.trace("Parameter date: {}", date);
        LOGGER.trace("Parameter databaseConnection: {}",  session);
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_TO_GET_FREE_MASTERS)) {
            statement.setString(1, DateUtil.getStringFromDate(date, true));
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get free masters");
        }
    }

    @Override
    public List<Master> getMasterSortByAlphabet(Session session) {
        LOGGER.debug("Method getMasterSortByAlphabet");
        LOGGER.trace("Parameter databaseConnection: {}",  session);
        return getMastersFromDatabase(SQL_REQUEST_TO_ALL_RECORDS_BY_ALPHABET, databaseConnection);
    }

    @Override
    public List<Master> getMasterSortByBusy(Session session) {
        LOGGER.debug("Method getMasterSortByBusy");
        LOGGER.trace("Parameter databaseConnection: {}",  session);
        return getMastersFromDatabase(SQL_REQUEST_TO_ALL_RECORDS_BY_BUSY, databaseConnection);
    }

    @Override
    public int getNumberMasters(Session session) {
        LOGGER.debug("Method getNumberMasters");
        LOGGER.trace("Parameter databaseConnection: {}",  session);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(SQL_REQUEST_TO_GET_NUMBER_RECORDS)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("number_masters");
            }
            return 0;
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get number masters");
        }
    }

    @Override
    public Master getMasterById(Long index, Session session) {
        LOGGER.debug("Method getMasterById");
        LOGGER.trace("Parameter index: {}", index);
        LOGGER.trace("Parameter databaseConnection: {}",  session);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(
            SQL_REQUEST_GET_MASTER_BY_ID)) {
            statement.setLong(1, index);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return parseResultSet(resultSet).get(0);
            }
            return null;
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get master by id");
        }
    }

    @Override
    protected void fillStatementCreate(PreparedStatement statement, Master master) {
        LOGGER.debug("Method fillStatementCreate");
        LOGGER.trace("Parameter statement: {}", statement);
        LOGGER.trace("Parameter master: {}", master);
        try {
            statement.setString(1, master.getName());
            statement.setInt(2, master.getNumberOrders());
            statement.setBoolean(3, master.getDelete());
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new DaoException("Error fill statement for create request");
        }
    }

    @Override
    protected void fillStatementUpdate(PreparedStatement statement, Master master) {
        LOGGER.debug("Method fillStatementUpdate");
        LOGGER.debug("Parameter statement: {}", statement);
        LOGGER.debug("Parameter master: {}", master);
        try {
            statement.setString(1, master.getName());
            statement.setInt(2, master.getNumberOrders());
            statement.setBoolean(3, master.getDelete());
            statement.setLong(4, master.getId());
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new DaoException("Error fill statement for update request");
        }
    }

    @Override
    protected void fillStatementUpdateAll(PreparedStatement statement, Master master) {
        LOGGER.debug("Method fillStatementUpdateAll");
        LOGGER.debug("Parameter statement: {}", statement);
        LOGGER.debug("Parameter master: {}", master);
        try {
            statement.setLong(1, master.getId());
            statement.setString(2, master.getName());
            statement.setInt(3, master.getNumberOrders());
            statement.setBoolean(4, master.getDelete());
            statement.setLong(5, master.getId());
            statement.setString(6, master.getName());
            statement.setInt(7, master.getNumberOrders());
            statement.setBoolean(8, master.getDelete());
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new DaoException("Error fill statement for update all records request");
        }
    }

    @Override
    protected void fillStatementDelete(PreparedStatement statement, Master master) {
        LOGGER.debug("Method fillStatementDelete");
        LOGGER.debug("Parameter statement: {}", statement);
        LOGGER.debug("Parameter master: {}", master);
        try {
            statement.setLong(1, master.getId());
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new DaoException("Error fill statement for delete request");
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
    protected List<Master> parseResultSet(ResultSet resultSet) {
        LOGGER.debug("Method parseResultSet");
        LOGGER.debug("Parameter propertyFileName: {}", resultSet);
        try {
            List<Master> masters = new ArrayList<>();
            while (resultSet.next()) {
                Master master = new Master();
                master.setId(resultSet.getLong("id"));
                master.setName(resultSet.getString("name"));
                master.setDelete(resultSet.getBoolean("is_deleted"));
                master.setNumberOrders(resultSet.getInt("number_orders"));
                masters.add(master);
            }
            return masters;
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request parse result record masters");
        }
    }

    private List<Master> getMastersFromDatabase(String request, Session session) {
        LOGGER.debug("Method getMastersFromDatabase");
        LOGGER.debug("Parameter request: {}", request);
        LOGGER.debug("Parameter databaseConnection: {}",  session);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get records masters");
        }
    }
}