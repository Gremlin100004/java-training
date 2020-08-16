package com.senla.carservice.dao;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
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
public class MasterDaoImpl implements MasterDao {
    @ConfigProperty
    private DatabaseConnection databaseConnection;
    private static final String SQL_REQUEST_TO_ADD_RECORD = "INSERT INTO masters VALUES (NULL, ?)";
    private static final String SQL_REQUEST_TO_UPDATE_RECORD = "UPDATE masters SET name=? WHERE id=?";
    private static final String SQL_REQUEST_TO_DELETE_RECORD = "DELETE FROM masters WHERE id=?";
    private static final String SQL_REQUEST_TO_GET_ALL_RECORDS = "SELECT * FROM masters";
    private static final String SQL_REQUEST_TO_GET_FREE_MASTERS = "SELECT DISTINCT masters.id, masters.name " +
                                                                  "FROM masters " +
                                                                  "INNER JOIN orders_masters " +
                                                                  "ON masters.id = orders_masters.master_id " +
                                                                  "LEFT JOIN orders " +
                                                                  "ON orders_masters.order_id = orders.id " +
                                                                  "WHERE orders.lead_time > ";

    @Override
    public void addMaster(Master master) {
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_TO_ADD_RECORD)) {
            statement.setString(1, master.getName());
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateMaster(Master master) {
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_TO_UPDATE_RECORD)) {
            statement.setString(1, master.getName());
            statement.setLong(2, master.getId());
            statement.execute();
        } catch (SQLException ex) {
            throw new BusinessException("Error request update record masters");
        }
    }

    @Override
    public void updateListMaster(List<Master> masters) {
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_TO_UPDATE_RECORD)) {
            for (Master master : masters) {
                statement.setString(1, master.getName());
                statement.setLong(2, master.getId());
                statement.execute();
            }
        } catch (SQLException ex) {
            throw new BusinessException("Error request update all records masters");
        }
    }

    @Override
    public void deleteMaster(Master master) {
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_TO_DELETE_RECORD)) {
            statement.setLong(1, master.getId());
            statement.execute();
        } catch (SQLException ex) {
            throw new BusinessException("Error request delete record masters");
        }
    }

    @Override
    public List<Master> getMasters() {
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            List<Master> masters = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SQL_REQUEST_TO_GET_ALL_RECORDS);
            while (resultSet.next()) {
                Master master = new Master();
                master.setId(resultSet.getLong("id"));
                master.setName(resultSet.getString("name"));
                masters.add(master);
            }
            return masters;

        } catch (SQLException ex) {
            throw new BusinessException("Error request get record masters");
        }
    }

    @Override
    public List<Master> getFreeMasters(Date date) {
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            List<Master> masters = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SQL_REQUEST_TO_GET_FREE_MASTERS + DateUtil.getStringFromDate(date, true));
            while (resultSet.next()) {
                Master master = new Master();
                master.setId(resultSet.getLong("id"));
                master.setName(resultSet.getString("name"));
                masters.add(master);
            }
            return masters;

        } catch (SQLException ex) {
            throw new BusinessException("Error request get record masters");
        }
    }
}