package com.senla.carservice.dao;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.domain.Master;
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
public class MasterDaoImpl extends AbstractDao <Master> implements MasterDao {
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
    private static final String SQL_REQUEST_TO_GET_FREE_MASTERS = "SELECT DISTINCT masters.id, masters.name, " +
            "masters.number_orders, masters.is_deleted FROM masters JOIN orders_masters ON masters.id = " +
            "orders_masters.master_id JOIN orders ON orders_masters.order_id = orders.id WHERE orders.lead_time > ? " +
            "UNION SELECT DISTINCT id, name, number_orders, is_deleted FROM masters WHERE number_orders = 0";

    public MasterDaoImpl() {
    }

    @Override
    public List<Master> getFreeMasters(Date date, DatabaseConnection databaseConnection) {
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_TO_GET_FREE_MASTERS)) {
            statement.setString(1, DateUtil.getStringFromDate(date, true));
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException ex) {
            throw new BusinessException("Error request get free masters");
        }
    }

    @Override
    public List<Master> getMasterSortByAlphabet(DatabaseConnection databaseConnection) {
        return getMastersFromDatabase(SQL_REQUEST_TO_ALL_RECORDS_BY_ALPHABET, databaseConnection);
    }

    @Override
    public List<Master> getMasterSortByBusy(DatabaseConnection databaseConnection) {
        return getMastersFromDatabase(SQL_REQUEST_TO_ALL_RECORDS_BY_BUSY, databaseConnection);
    }

    @Override
    public int getNumberMasters(DatabaseConnection databaseConnection) {
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_REQUEST_TO_GET_NUMBER_RECORDS);
            if (resultSet.next()) {
                return resultSet.getInt("number_masters");
            }
            return 0;
        } catch (SQLException ex) {
            throw new BusinessException("Error request get number masters");
        }
    }

    @Override
    protected void fillStatementCreate(PreparedStatement statement, Master master) {
        try {
            statement.setString(1, master.getName());
            statement.setInt(2, master.getNumberOrders());
            statement.setBoolean(3, master.getDelete());
        } catch (SQLException e) {
            throw new BusinessException("Error fill statement for create request");
        }
    }

    @Override
    protected void fillStatementUpdate(PreparedStatement statement, Master master) {
        try {
            statement.setString(1, master.getName());
            statement.setInt(2, master.getNumberOrders());
            statement.setBoolean(3, master.getDelete());
            statement.setLong(4, master.getId());
        } catch (SQLException e) {
            throw new BusinessException("Error fill statement for update request");
        }
    }

    @Override
    protected void fillStatementUpdateAll(PreparedStatement statement, Master master) {
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
            throw new BusinessException("Error fill statement for update all records request");
        }
    }

    @Override
    protected void fillStatementDelete(PreparedStatement statement, Master master) {
        try {
            statement.setLong(1, master.getId());
        } catch (SQLException e) {
            throw new BusinessException("Error fill statement for delete request");
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
            throw new BusinessException("Error request parse result record masters");
        }
    }

    private List<Master> getMastersFromDatabase(String request, DatabaseConnection databaseConnection) {
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException ex) {
            throw new BusinessException("Error request get records masters");
        }
    }
}