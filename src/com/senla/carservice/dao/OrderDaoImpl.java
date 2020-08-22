package com.senla.carservice.dao;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.ConstructorDependency;
import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.enumaration.Status;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.DateUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class OrderDaoImpl extends AbstractDao implements OrderDao {
    private static final String SQL_REQUEST_TO_ADD_RECORD = "INSERT INTO orders VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_REQUEST_TO_ADD_RECORD_TABLE_ORDERS_MASTERS = "INSERT INTO orders_masters VALUES (?, ?)";
    private static final String SQL_NULL_DATE = "1111-01-01 00:00";
    private static final int SQL_DEFAULT_PLACE_ID = 1;
    private static final String SQL_REQUEST_TO_UPDATE_RECORD = "UPDATE orders SET creation_time=?, execution_start_time=?, " +
        "lead_time=?, automaker=?, model=?, registration_number=?, price=?, status=?, delete_status=?, place_id=? " +
        "WHERE id=?";
    private static final String SQL_REQUEST_TO_UPDATE_RECORDS_IF_EXIST = "INSERT INTO masters(id, creation_time, " +
        "execution_start_time, lead_time, automaker, model, registration_number, price, status, delete_status, place_id) " +
        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = ?, creation_time = ?, execution_start_time = ?, " +
        "lead_time = ?, automaker = ?, model = ?, registration_number = ?, price = ?, status = ?, delete_status = ?, place_id = ?";
    private static final String SQL_REQUEST_TO_GET_ALL_RECORDS = "SELECT orders.id, orders.creation_time, " +
        "orders.execution_start_time, orders.lead_time, orders.automaker, orders.model, orders.registration_number, " +
        "orders.price, orders.status, orders.delete_status, places.id AS order_place_id, places.number AS place_number, " +
        "places.busy_status AS place_busy_status, places.delete_status AS place_delete_status FROM orders JOIN " +
        "places ON orders.place_id = places.id";
    private static final String SQL_REQUEST_TO_GET_LAST_RECORD = "SELECT orders.id, orders.creation_time, " +
        "orders.execution_start_time, orders.lead_time, orders.automaker, orders.model, orders.registration_number, " +
        "orders.price, orders.status, orders.delete_status, places.id AS order_place_id, places.number AS place_number, " +
        "places.busy_status AS place_busy_status, places.delete_status AS place_delete_status FROM orders JOIN " +
        "places ON orders.place_id = places.id ORDER BY orders.id DESC LIMIT 1";
    private static final String SQL_REQUEST_TO_DELETE_RECORD = "UPDATE orders SET delete_status=true WHERE id=?";
    private static final String SQL_REQUEST_TO_GET_NUMBER_FREE_MASTERS = "SELECT COUNT(masters.id) AS amount_of_elements " +
        "FROM orders JOIN orders_masters ON orders_masters.order_id = orders.id JOIN masters ON orders_masters.master_id = " +
        "masters.id WHERE orders.lead_time > '";
    private static final String SQL_REQUEST_TO_GET_NUMBER_FREE_PLACES = "SELECT COUNT(places.id) AS amount_of_elements FROM " +
        "orders JOIN places ON places.id = orders.place_id WHERE orders.lead_time > '";
    private static final String SQL_CONDITION_END_TIME = " AND orders.lead_time < '";
    private static final String SQL_CONDITION_START_TIME = " AND orders.lead_time > '";
    private static final String SQL_END_CONDITION = "'";
    private static final String SQL_REQUEST_TO_GET_ORDER_MASTER = "SELECT masters.id, masters.name, masters.number_orders, " +
        "masters.delete_status FROM masters JOIN orders_masters ON orders_masters.master_id = masters.id " +
        "WHERE orders_masters.order_id=";
    private static final String SQL_REQUEST_SORT_BY_PRICE = " ORDER BY price";
    private static final String SQL_REQUEST_SORT_FILING_DATE = " ORDER BY creation_time";
    private static final String SQL_REQUEST_SORT_EXECUTION_DATE = " ORDER BY lead_time";
    private static final String SQL_REQUEST_SORT_PLANNED_START_DATE = " ORDER BY execution_start_time";
    private static final String SQL_REQUEST_EXECUTE_ORDERS = " WHERE orders.status='PERFORM'";
    private static final String SQL_REQUEST_COMPLETED_ORDERS = " WHERE orders.status='COMPLETED'";
    private static final String SQL_REQUEST_CANCELED_ORDERS = " WHERE orders.status='CANCELED'";
    private static final String SQL_REQUEST_DELETED_ORDERS = " WHERE orders.status='DELETED'";
    private static final String SQL_REQUEST_GET_MASTER_ORDERS = "SELECT orders.id, orders.creation_time, " +
        "orders.execution_start_time, orders.lead_time, orders.automaker, orders.model, orders.registration_number, " +
        "orders.price, orders.status, orders.delete_status, places.id AS order_place_id, places.number AS place_number, " +
        "places.busy_status AS place_busy_status, places.delete_status AS place_delete_status  FROM orders JOIN " +
        "orders_masters ON orders_masters.order_id = orders.id JOIN places ON orders.place_id=places.id " +
        "WHERE orders_masters.master_id=?";

    @ConstructorDependency
    public OrderDaoImpl(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

    public OrderDaoImpl() {
    }

    @Override
    public Order getLastOrder() {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_LAST_RECORD).get(0);
    }

    @Override
    public int getNumberBusyMasters(String startPeriod, String endPeriod) {
        return getIntFromRequest(SQL_REQUEST_TO_GET_NUMBER_FREE_MASTERS + startPeriod + SQL_END_CONDITION +
                                 SQL_CONDITION_END_TIME + endPeriod + SQL_END_CONDITION);
    }

    @Override
    public int getNumberBusyPlaces(String startPeriod, String endPeriod) {
        return getIntFromRequest(SQL_REQUEST_TO_GET_NUMBER_FREE_PLACES + startPeriod + SQL_END_CONDITION +
                                 SQL_CONDITION_END_TIME + endPeriod + SQL_END_CONDITION);
    }

    public void addRecordToTableManyToMany(Order order, Master master) {
        System.out.println(order.getId());
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(
            SQL_REQUEST_TO_ADD_RECORD_TABLE_ORDERS_MASTERS)) {
            statement.setLong(1, order.getId());
            statement.setLong(2, master.getId());
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new BusinessException("Error request update records table orders_masters");
        }
    }

    @Override
    public List<Order> getOrdersSortByFilingDate() {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_SORT_FILING_DATE);
    }

    @Override
    public List<Order> getOrdersSortByExecutionDate() {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_SORT_EXECUTION_DATE);
    }

    @Override
    public List<Order> getOrdersSortByPlannedStartDate() {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_SORT_PLANNED_START_DATE);
    }

    @Override
    public List<Order> getOrdersSortByPrice() {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_SORT_BY_PRICE);
    }

    @Override
    public List<Order> getExecuteOrderSortByFilingDate() {
        return getOrdersFromDatabase(
            SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_EXECUTE_ORDERS + SQL_REQUEST_SORT_FILING_DATE);
    }

    @Override
    public List<Order> getExecuteOrderSortExecutionDate() {
        return getOrdersFromDatabase(
            SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_EXECUTE_ORDERS + SQL_REQUEST_SORT_EXECUTION_DATE);
    }

    @Override
    public List<Order> getCompletedOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate) {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_COMPLETED_ORDERS +
                                     SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION +
                                     SQL_CONDITION_END_TIME + endPeriodDate +
                                     SQL_END_CONDITION + SQL_REQUEST_SORT_FILING_DATE);
    }

    @Override
    public List<Order> getCompletedOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate) {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_COMPLETED_ORDERS +
                                     SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION +
                                     SQL_CONDITION_END_TIME + endPeriodDate +
                                     SQL_END_CONDITION + SQL_REQUEST_SORT_EXECUTION_DATE);
    }

    @Override
    public List<Order> getCompletedOrdersSortByPrice(String startPeriodDate, String endPeriodDate) {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_COMPLETED_ORDERS +
                                     SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION +
                                     SQL_CONDITION_END_TIME + endPeriodDate +
                                     SQL_END_CONDITION + SQL_REQUEST_SORT_BY_PRICE);
    }

    @Override
    public List<Order> getCanceledOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate) {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_CANCELED_ORDERS +
                                     SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION +
                                     SQL_CONDITION_END_TIME + endPeriodDate +
                                     SQL_END_CONDITION + SQL_REQUEST_SORT_FILING_DATE);
    }

    @Override
    public List<Order> getCanceledOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate) {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_CANCELED_ORDERS +
                                     SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION +
                                     SQL_CONDITION_END_TIME + endPeriodDate +
                                     SQL_END_CONDITION + SQL_REQUEST_SORT_EXECUTION_DATE);
    }

    @Override
    public List<Order> getCanceledOrdersSortByPrice(String startPeriodDate, String endPeriodDate) {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_CANCELED_ORDERS +
                                     SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION +
                                     SQL_CONDITION_END_TIME + endPeriodDate +
                                     SQL_END_CONDITION + SQL_REQUEST_SORT_BY_PRICE);
    }

    @Override
    public List<Order> getDeletedOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate) {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_DELETED_ORDERS +
                                     SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION +
                                     SQL_CONDITION_END_TIME + endPeriodDate +
                                     SQL_END_CONDITION + SQL_REQUEST_SORT_FILING_DATE);
    }

    @Override
    public List<Order> getDeletedOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate) {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_DELETED_ORDERS +
                                     SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION +
                                     SQL_CONDITION_END_TIME + endPeriodDate +
                                     SQL_END_CONDITION + SQL_REQUEST_SORT_EXECUTION_DATE);
    }

    @Override
    public List<Order> getDeletedOrdersSortByPrice(String startPeriodDate, String endPeriodDate) {
        return getOrdersFromDatabase(SQL_REQUEST_TO_GET_ALL_RECORDS + SQL_REQUEST_DELETED_ORDERS +
                                     SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION +
                                     SQL_CONDITION_END_TIME + endPeriodDate +
                                     SQL_END_CONDITION + SQL_REQUEST_SORT_BY_PRICE);
    }

    @Override
    public List<Order> getMasterOrders(Master master) {
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_GET_MASTER_ORDERS)) {
            statement.setLong(1, master.getId());
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException ex) {
            throw new BusinessException("Error request get master orders");
        }
    }

    @Override
    public List<Master> getOrderMasters(Order order) {
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_REQUEST_TO_GET_ORDER_MASTER + order.getId());
            List<Master> masters = new ArrayList<>();
            while (resultSet.next()) {
                Master master = new Master();
                master.setId(resultSet.getLong("id"));
                master.setName(resultSet.getString("name"));
                master.setDelete(resultSet.getBoolean("delete_status"));
                master.setNumberOrders(resultSet.getInt("number_orders"));
                masters.add(master);
            }
            return masters;
        } catch (SQLException ex) {
            throw new BusinessException("Error request get records order masters");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<Order> parseResultSet(ResultSet resultSet) {
        try {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order(resultSet.getString("automaker"),
                                        resultSet.getString("model"), resultSet.getString("registration_number"));
                order.setCreationTime(resultSet.getDate("creation_time"));
                order.setExecutionStartTime(resultSet.getDate("execution_start_time"));
                order.setLeadTime(resultSet.getDate("lead_time"));
                order.setPrice(resultSet.getBigDecimal("price"));
                order.setStatus(Status.valueOf(resultSet.getString("status")));
                order.setDeleteStatus(resultSet.getBoolean("delete_status"));
                Place place = new Place();
                place.setId(resultSet.getLong("order_place_id"));
                place.setNumber(resultSet.getInt("place_number"));
                place.setBusyStatus(resultSet.getBoolean("place_busy_status"));
                place.setDelete(resultSet.getBoolean("place_delete_status"));
                order.setPlace(place);
                order.setId(resultSet.getLong("id"));
                orders.add(order);
            }
            return orders;
        } catch (SQLException ex) {
            throw new BusinessException("Error request get records orders");
        }
    }

    @Override
    protected <T> void fillStatementCreate(PreparedStatement statement, T object) {
        Order order = (Order) object;
        try {
            statement.setString(1, DateUtil.getStringFromDate(order.getCreationTime(), true));
            statement.setString(2, SQL_NULL_DATE);
            statement.setString(3, SQL_NULL_DATE);
            statement.setString(4, order.getAutomaker());
            statement.setString(5, order.getModel());
            statement.setString(6, order.getRegistrationNumber());
            statement.setBigDecimal(7, order.getPrice());
            statement.setString(8, String.valueOf(order.getStatus()));
            statement.setBoolean(9, order.isDeleteStatus());
            statement.setLong(10, SQL_DEFAULT_PLACE_ID);
        } catch (SQLException e) {
            throw new BusinessException("Error fill statement for create request");
        }
    }

    @Override
    protected <T> void fillStatementUpdate(PreparedStatement statement, T object) {
        Order order = (Order) object;
        try {
            statement.setString(1, DateUtil.getStringFromDate(order.getCreationTime(), true));
            statement.setString(2, DateUtil.getStringFromDate(order.getExecutionStartTime(), true));
            statement.setString(3, DateUtil.getStringFromDate(order.getLeadTime(), true));
            statement.setString(4, order.getAutomaker());
            statement.setString(5, order.getModel());
            statement.setString(6, order.getRegistrationNumber());
            statement.setBigDecimal(7, order.getPrice());
            statement.setString(8, String.valueOf(order.getStatus()));
            statement.setBoolean(9, order.isDeleteStatus());
            statement.setLong(10, order.getPlace().getId());
            statement.setLong(11, order.getId());
        } catch (SQLException e) {
            throw new BusinessException("Error fill statement for update request");
        }
    }

    @Override
    protected <T> void fillStatementUpdateAll(PreparedStatement statement, T object) {
        Order order = (Order) object;
        try {
            statement.setLong(1, order.getId());
            statement.setString(2, DateUtil.getStringFromDate(order.getCreationTime(), true));
            statement.setString(3, DateUtil.getStringFromDate(order.getExecutionStartTime(), true));
            statement.setString(4, DateUtil.getStringFromDate(order.getLeadTime(), true));
            statement.setString(5, order.getAutomaker());
            statement.setString(6, order.getModel());
            statement.setString(7, order.getRegistrationNumber());
            statement.setBigDecimal(8, order.getPrice());
            statement.setString(9, String.valueOf(order.getStatus()));
            statement.setBoolean(10, order.isDeleteStatus());
            statement.setLong(11, order.getPlace().getId());
            statement.setLong(12, order.getId());
            statement.setString(13, DateUtil.getStringFromDate(order.getCreationTime(), true));
            statement.setString(14, DateUtil.getStringFromDate(order.getExecutionStartTime(), true));
            statement.setString(15, DateUtil.getStringFromDate(order.getLeadTime(), true));
            statement.setString(16, order.getAutomaker());
            statement.setString(17, order.getModel());
            statement.setString(18, order.getRegistrationNumber());
            statement.setBigDecimal(19, order.getPrice());
            statement.setString(20, String.valueOf(order.getStatus()));
            statement.setBoolean(21, order.isDeleteStatus());
            statement.setLong(22, order.getPlace().getId());
        } catch (SQLException e) {
            throw new BusinessException("Error fill statement for update request");
        }
    }

    @Override
    protected <T> void fillStatementDelete(PreparedStatement statement, T object) {
        Order order = (Order) object;
        try {
            statement.setLong(1, order.getId());
        } catch (SQLException e) {
            throw new BusinessException("Error fill statement for update request");
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

    private List<Order> getOrdersFromDatabase(String request) {
        System.out.println(request);
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(request);
            return parseResultSet(resultSet);
        } catch (SQLException ex) {
            throw new BusinessException("Error request get records orders");
        }
    }

    private int getIntFromRequest(String request) {
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(request);
            resultSet.next();
            return resultSet.getInt("amount_of_elements");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new BusinessException("Error request get records orders");
        }
    }
}