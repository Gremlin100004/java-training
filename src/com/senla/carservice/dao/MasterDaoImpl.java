package com.senla.carservice.dao;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.ConstructorDependency;
import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.domain.Master;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.DateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class MasterDaoImpl extends AbstractDao implements MasterDao {
    private static final String SQL_REQUEST_TO_ADD_RECORD = "INSERT INTO masters VALUES (";
    private static final String END_REQUEST_TO_ADD_RECORD = ")";
    private static final String SEPARATOR = ",";
    private static final String SQL_REQUEST_TO_UPDATE_RECORD = "UPDATE masters SET name=";
    private static final String FIELD_DELETE_STATUS = " delete_status=";
    private static final String PRIMARY_KEY_FIELD = " WHERE id=";
    private static final String SQL_REQUEST_TO_DELETE_RECORD = "UPDATE masters SET delete_status=true WHERE id=";
    private static final String SQL_REQUEST_TO_GET_ALL_RECORDS = "SELECT * FROM masters";
    private static final String SQL_REQUEST_TO_GET_FREE_MASTERS = "SELECT DISTINCT masters.id, masters.name " +
            "FROM masters INNER JOIN orders_masters ON masters.id = orders_masters.master_id LEFT JOIN orders " +
            "ON orders_masters.order_id = orders.id WHERE orders.lead_time > ";



    @ConstructorDependency
    public MasterDaoImpl(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

    public MasterDaoImpl() {
    }

    @Override
    public List<Master> getFreeMasters(Date date) {
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            List<Master> masters = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SQL_REQUEST_TO_GET_FREE_MASTERS + DateUtil.getStringFromDate(
                    date, true));
            while (resultSet.next()) {
                Master master = new Master();
                master.setId(resultSet.getLong("id"));
                master.setName(resultSet.getString("name"));
                master.setDelete(resultSet.getBoolean("delete_status"));
                masters.add(master);
            }
            return masters;
        } catch (SQLException ex) {
            throw new BusinessException("Error request get record masters");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<Master> parseResultSet(ResultSet resultSet) {
        try {
            List<Master> masters = new ArrayList<>();
            while (resultSet.next()) {
                Master master = new Master();
                master.setId(resultSet.getLong("id"));
                master.setName(resultSet.getString("name"));
                master.setDelete(resultSet.getBoolean("delete_status"));
                masters.add(master);
            }
            return masters;
        } catch (SQLException ex) {
            throw new BusinessException("Error request get record masters");
        }
    }

    @Override
    protected <T> String getCreateRequest(T object) {
        Master master = (Master) object;
        return SQL_REQUEST_TO_ADD_RECORD + master.getName() + SEPARATOR + master.getDelete() + SEPARATOR +
                END_REQUEST_TO_ADD_RECORD;
    }

    @Override
    protected String getReadAllRequest() {
        return SQL_REQUEST_TO_GET_ALL_RECORDS;
    }

    @Override
    protected String getUpdateRequest(Object object) {
        Master master = (Master) object;
        return SQL_REQUEST_TO_UPDATE_RECORD + master.getName() + FIELD_DELETE_STATUS + master.getDelete() +
                PRIMARY_KEY_FIELD + master.getId();
    }

    @Override
    protected String getDeleteRequest(Object object) {
        Master master = (Master) object;
        return SQL_REQUEST_TO_DELETE_RECORD + master.getId();
    }
}