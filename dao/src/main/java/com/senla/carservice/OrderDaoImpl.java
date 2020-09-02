package com.senla.carservice;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.connection.DatabaseConnection;
import com.senla.carservice.dateutil.DateUtil;
import com.senla.carservice.service.enumaration.Status;
import com.senla.carservice.dao.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class OrderDaoImpl extends AbstractDao <Order> implements OrderDao {

    private static final String SQL_REQUEST_TO_ADD_RECORD = "INSERT INTO orders VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_REQUEST_TO_ADD_RECORD_TABLE_ORDERS_MASTERS = "INSERT INTO orders_masters VALUES (?, ?)";
    private static final String SQL_NULL_DATE = "1111-01-01 00:00";
    private static final int SQL_DEFAULT_PLACE_ID = 1;
    private static final String SQL_REQUEST_TO_UPDATE_RECORD = "UPDATE orders SET creation_time=?, execution_start_time=?, " +
        "lead_time=?, automaker=?, model=?, registration_number=?, price=?, status=?, is_deleted=?, place_id=? " +
        "WHERE id=?";
    private static final String SQL_REQUEST_TO_UPDATE_RECORDS_IF_EXIST = "INSERT INTO orders(id, creation_time, " +
        "execution_start_time, lead_time, automaker, model, registration_number, price, status, is_deleted, place_id) " +
        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = ?, creation_time = ?, execution_start_time = ?, " +
        "lead_time = ?, automaker = ?, model = ?, registration_number = ?, price = ?, status = ?, is_deleted = ?, place_id = ?";
    private static final String SQL_REQUEST_TO_GET_ALL_RECORDS = "SELECT * FROM (SELECT DISTINCT * FROM orders_masters  " +
        "LEFT JOIN (SELECT orders.id AS orders_order_id, orders.creation_time, orders.execution_start_time, " +
        "orders.lead_time, orders.automaker, orders.model, orders.registration_number, orders.price, orders.status, " +
        "orders.is_deleted AS orders_is_deleted, orders.place_id, places.id AS places_place_id, places.number, " +
        "places.is_busy AS place_is_busy, places.is_deleted AS place_is_deleted FROM orders JOIN places ON " +
        "orders.place_id = places.id) AS orders_with_place ON orders_masters.order_id = orders_with_place.orders_order_id " +
        "UNION SELECT DISTINCT * FROM orders_masters RIGHT JOIN (SELECT orders.id AS orders_order_id, " +
        "orders.creation_time, orders.execution_start_time, orders.lead_time, orders.automaker, orders.model, " +
        "orders.registration_number, orders.price, orders.status, orders.is_deleted AS orders_is_deleted, orders.place_id, " +
        "places.id AS places_place_id, places.number, places.is_busy AS place_is_busy, places.is_deleted AS " +
        "place_is_deleted FROM orders JOIN places ON orders.place_id = places.id) AS orders_with_place ON " +
        "orders_masters.order_id = orders_with_place.orders_order_id) AS full_orders_masters LEFT OUTER JOIN masters ON " +
        "masters.id = full_orders_masters.master_id UNION SELECT * FROM (SELECT DISTINCT * FROM orders_masters LEFT JOIN " +
        "(SELECT orders.id AS orders_order_id, orders.creation_time, orders.execution_start_time, orders.lead_time, " +
        "orders.automaker, orders.model, orders.registration_number, orders.price, orders.status, orders.is_deleted AS " +
        "orders_is_deleted, orders.place_id, places.id AS places_place_id, places.number, places.is_busy AS " +
        "place_is_busy, places.is_deleted AS place_is_deleted FROM orders JOIN places ON orders.place_id = places.id) AS " +
        "orders_with_place ON orders_masters.order_id = orders_with_place.orders_order_id UNION SELECT DISTINCT * FROM " +
        "orders_masters RIGHT JOIN (SELECT orders.id AS orders_order_id, orders.creation_time, " +
        "orders.execution_start_time, orders.lead_time, orders.automaker, orders.model, orders.registration_number, " +
        "orders.price, orders.status, orders.is_deleted AS orders_is_deleted, orders.place_id, places.id AS " +
        "places_place_id, places.number, places.is_busy AS place_is_busy, places.is_deleted AS place_is_deleted FROM " +
        "orders JOIN places ON orders.place_id = places.id) AS orders_with_place ON orders_masters.order_id = " +
        "orders_with_place.orders_order_id) AS full_orders_masters RIGHT OUTER JOIN masters ON masters.id = " +
        "full_orders_masters.master_id";
    private static final String SQL_REQUEST_TO_GET_ALL_RECORDS_CONDITION = "SELECT * FROM (SELECT DISTINCT * FROM orders_masters " +
        "LEFT JOIN (%s) AS orders_with_place ON orders_masters.order_id = orders_with_place.orders_order_id UNION SELECT " +
        "DISTINCT * FROM orders_masters RIGHT JOIN (%s) AS orders_with_place ON orders_masters.order_id = " +
        "orders_with_place.orders_order_id) AS full_orders_masters LEFT OUTER JOIN masters ON masters.id = " +
        "full_orders_masters.master_id UNION SELECT * FROM (SELECT DISTINCT * FROM orders_masters LEFT JOIN (%s) AS " +
        "orders_with_place ON orders_masters.order_id = orders_with_place.orders_order_id UNION SELECT DISTINCT * FROM " +
        "orders_masters RIGHT JOIN (%s) AS orders_with_place ON orders_masters.order_id = " +
        "orders_with_place.orders_order_id) AS full_orders_masters RIGHT OUTER JOIN masters ON masters.id = " +
        "full_orders_masters.master_id %s";
    private static final String SQL_REQUEST_TO_GET_ORDERS = "SELECT orders.id AS orders_order_id, orders.creation_time, " +
            "orders.execution_start_time, orders.lead_time, orders.automaker, orders.model, orders.registration_number, " +
            "orders.price, orders.status, orders.is_deleted AS orders_is_deleted, orders.place_id, places.id AS " +
            "places_place_id, places.number, places.is_busy AS place_is_busy, places.is_deleted AS place_is_deleted FROM " +
            "orders JOIN places ON orders.place_id = places.id";
    private static final String SQL_REQUEST_TO_DELETE_RECORD = "UPDATE orders SET is_deleted=true WHERE id=?";
    private static final String SQL_REQUEST_TO_GET_NUMBER_BUSY_MASTERS = "SELECT COUNT(masters.id) AS amount_of_elements " +
        "FROM orders JOIN orders_masters ON orders_masters.order_id = orders.id JOIN masters ON orders_masters.master_id = " +
        "masters.id WHERE orders.lead_time > '";
    private static final String SQL_REQUEST_TO_GET_NUMBER_BUSY_PLACES = "SELECT COUNT(places.id) AS amount_of_elements FROM " +
        "orders JOIN places ON places.id = orders.place_id WHERE orders.lead_time > '";
    private static final String SQL_CONDITION_END_TIME = " AND orders.lead_time < '";
    private static final String SQL_CONDITION_START_TIME = " AND orders.lead_time > '";
    private static final String SQL_END_CONDITION = "'";
    private static final String SQL_REQUEST_SORT_BY_PRICE = "ORDER BY price";
    private static final String CONDITION_SORT_BY_ID = "ORDER BY orders_order_id DESC";
    private static final String CONDITION_SORT_FILING_DATE = "ORDER BY creation_time";
    private static final String CONDITION_SORT_EXECUTION_DATE = "ORDER BY lead_time";
    private static final String CONDITION_SORT_PLANNED_START_DATE = "ORDER BY execution_start_time";
    private static final String CONDITION_EXECUTE_ORDERS = " WHERE orders.status='PERFORM'";
    private static final String CONDITION_COMPLETED_ORDERS = " WHERE orders.status='COMPLETED'";
    private static final String CONDITION_CANCELED_ORDERS = " WHERE orders.status='CANCELED'";
    private static final String CONDITION_DELETED_ORDERS = " WHERE orders.status='DELETED'";
    private static final String SQL_REQUEST_GET_MASTER_ORDERS = "SELECT * FROM (SELECT DISTINCT * FROM orders_masters  " +
        "LEFT JOIN (SELECT orders.id AS orders_order_id, orders.creation_time, orders.execution_start_time, " +
        "orders.lead_time, orders.automaker, orders.model, orders.registration_number, orders.price, orders.status, " +
        "orders.is_deleted AS orders_is_deleted, orders.place_id, places.id AS places_place_id, places.number, " +
        "places.is_busy AS place_is_busy, places.is_deleted AS place_is_deleted FROM orders JOIN places ON " +
        "orders.place_id = places.id) AS orders_with_place ON orders_masters.order_id = orders_with_place.orders_order_id " +
        "WHERE master_id=? UNION SELECT DISTINCT * FROM orders_masters RIGHT JOIN (SELECT orders.id AS orders_order_id, " +
        "orders.creation_time, orders.execution_start_time, orders.lead_time, orders.automaker, orders.model, " +
        "orders.registration_number, orders.price, orders.status, orders.is_deleted AS orders_is_deleted, orders.place_id, " +
        "places.id AS places_place_id, places.number, places.is_busy AS place_is_busy, places.is_deleted AS " +
        "place_is_deleted FROM orders JOIN places ON orders.place_id = places.id) AS orders_with_place ON " +
        "orders_masters.order_id = orders_with_place.orders_order_id) AS full_orders_masters LEFT OUTER JOIN masters ON " +
        "masters.id = full_orders_masters.master_id WHERE master_id=? UNION SELECT * FROM (SELECT DISTINCT * FROM " +
        "orders_masters LEFT JOIN (SELECT orders.id AS orders_order_id, orders.creation_time, orders.execution_start_time, " +
        "orders.lead_time, orders.automaker, orders.model, orders.registration_number, orders.price, orders.status, " +
        "orders.is_deleted AS orders_is_deleted, orders.place_id, places.id AS places_place_id, places.number, " +
        "places.is_busy AS place_is_busy, places.is_deleted AS place_is_deleted FROM orders JOIN places ON " +
        "orders.place_id = places.id) AS orders_with_place ON orders_masters.order_id = orders_with_place.orders_order_id " +
        "WHERE master_id=? UNION SELECT DISTINCT * FROM orders_masters RIGHT JOIN (SELECT orders.id AS orders_order_id, " +
        "orders.creation_time, orders.execution_start_time, orders.lead_time, orders.automaker, orders.model, " +
        "orders.registration_number, orders.price, orders.status, orders.is_deleted AS orders_is_deleted, " +
        "orders.place_id, places.id AS places_place_id, places.number, places.is_busy AS place_is_busy, places.is_deleted " +
        "AS place_is_deleted FROM orders JOIN places ON orders.place_id = places.id) AS orders_with_place ON " +
        "orders_masters.order_id = orders_with_place.orders_order_id) AS full_orders_masters RIGHT OUTER JOIN masters " +
        "ON masters.id = full_orders_masters.master_id WHERE master_id=?";
    private static final String SQL_REQUEST_TO_GET_NUMBER_RECORDS = "SELECT COUNT(orders.id) AS number_orders FROM orders;";
    private static final String SQL_REQUEST_ORDER_BY_ID = "SELECT DISTINCT id FROM orders WHERE id=?";

    public OrderDaoImpl() {
    }

    @Override
    public Order getLastOrder(DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getLastOrder");
        LOGGER.trace("Parameter propertyFileName: {}", databaseConnection);
        List<Order> orders = getOrdersFromDatabase("", CONDITION_SORT_BY_ID, databaseConnection);
        return orders.get(0);
    }

    @Override
    public int getNumberBusyMasters(String startPeriod, String endPeriod, DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getNumberBusyMasters");
        LOGGER.trace("Parameter startPeriod: {}", startPeriod);
        LOGGER.trace("Parameter endPeriod: {}", endPeriod);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getIntFromRequest(SQL_REQUEST_TO_GET_NUMBER_BUSY_MASTERS + startPeriod + SQL_END_CONDITION +
           SQL_CONDITION_END_TIME + endPeriod + SQL_END_CONDITION, databaseConnection);
    }

    @Override
    public int getNumberBusyPlaces(String startPeriod, String endPeriod, DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getNumberBusyPlaces");
        LOGGER.trace("Parameter startPeriod: {}", startPeriod);
        LOGGER.trace("Parameter endPeriod: {}", endPeriod);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getIntFromRequest(SQL_REQUEST_TO_GET_NUMBER_BUSY_PLACES + startPeriod + SQL_END_CONDITION +
           SQL_CONDITION_END_TIME + endPeriod + SQL_END_CONDITION, databaseConnection);
    }

    @Override
    public void addRecordToTableManyToMany(Order order, DatabaseConnection databaseConnection) {
        LOGGER.debug("Method addRecordToTableManyToMany");
        LOGGER.trace("Parameter order: {}", order);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(
            SQL_REQUEST_TO_ADD_RECORD_TABLE_ORDERS_MASTERS)) {
            statement.setLong(1, order.getId());
            for (Master master: order.getMasters()) {
                statement.setLong(2, master.getId());
                statement.execute();
            }
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request update records table orders_masters");
        }
    }

    @Override
    public List<Order> getOrdersSortByFilingDate(DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getOrdersSortByFilingDate");
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase("", CONDITION_SORT_FILING_DATE,
                databaseConnection);
    }

    @Override
    public List<Order> getOrdersSortByExecutionDate(DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getOrdersSortByExecutionDate");
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase("", CONDITION_SORT_EXECUTION_DATE,
                databaseConnection);
    }

    @Override
    public List<Order> getOrdersSortByPlannedStartDate(DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getOrdersSortByPlannedStartDate");
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase("", CONDITION_SORT_PLANNED_START_DATE,
                databaseConnection);
    }

    @Override
    public List<Order> getOrdersSortByPrice(DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getOrdersSortByPrice");
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase("", SQL_REQUEST_SORT_BY_PRICE, databaseConnection);
    }

    @Override
    public List<Order> getExecuteOrderSortByFilingDate(DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getExecuteOrderSortByFilingDate");
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase(CONDITION_EXECUTE_ORDERS, CONDITION_SORT_FILING_DATE, databaseConnection);
    }

    @Override
    public List<Order> getExecuteOrderSortExecutionDate(DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getExecuteOrderSortExecutionDate");
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase(CONDITION_EXECUTE_ORDERS, CONDITION_SORT_EXECUTION_DATE, databaseConnection);
    }

    @Override
    public List<Order> getCompletedOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate,
                                                          DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getCompletedOrdersSortByFilingDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase(CONDITION_COMPLETED_ORDERS +
           SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION + SQL_CONDITION_END_TIME + endPeriodDate +
           SQL_END_CONDITION, CONDITION_SORT_FILING_DATE, databaseConnection);
    }

    @Override
    public List<Order> getCompletedOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate,
                                                             DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getCompletedOrdersSortByExecutionDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase(CONDITION_COMPLETED_ORDERS +
           SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION + SQL_CONDITION_END_TIME + endPeriodDate +
           SQL_END_CONDITION, CONDITION_SORT_EXECUTION_DATE, databaseConnection);
    }

    @Override
    public List<Order> getCompletedOrdersSortByPrice(String startPeriodDate, String endPeriodDate,
                                                     DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getCompletedOrdersSortByPrice");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase(CONDITION_COMPLETED_ORDERS +
           SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION + SQL_CONDITION_END_TIME + endPeriodDate +
           SQL_END_CONDITION, SQL_REQUEST_SORT_BY_PRICE, databaseConnection);
    }

    @Override
    public List<Order> getCanceledOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate,
                                                         DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getCanceledOrdersSortByFilingDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase(CONDITION_CANCELED_ORDERS +
           SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION + SQL_CONDITION_END_TIME + endPeriodDate +
           SQL_END_CONDITION, CONDITION_SORT_FILING_DATE, databaseConnection);
    }

    @Override
    public List<Order> getCanceledOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate,
                                                            DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getCanceledOrdersSortByExecutionDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase(CONDITION_CANCELED_ORDERS +
           SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION + SQL_CONDITION_END_TIME + endPeriodDate +
           SQL_END_CONDITION, CONDITION_SORT_EXECUTION_DATE, databaseConnection);
    }

    @Override
    public List<Order> getCanceledOrdersSortByPrice(String startPeriodDate, String endPeriodDate,
                                                    DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getCanceledOrdersSortByPrice");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase(CONDITION_CANCELED_ORDERS +
           SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION + SQL_CONDITION_END_TIME + endPeriodDate +
           SQL_END_CONDITION, SQL_REQUEST_SORT_BY_PRICE, databaseConnection);
    }

    @Override
    public List<Order> getDeletedOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate,
                                                        DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getDeletedOrdersSortByFilingDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase(CONDITION_DELETED_ORDERS +
           SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION + SQL_CONDITION_END_TIME + endPeriodDate +
           SQL_END_CONDITION, CONDITION_SORT_FILING_DATE, databaseConnection);
    }

    @Override
    public List<Order> getDeletedOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate, DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getDeletedOrdersSortByExecutionDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        return getOrdersFromDatabase(CONDITION_DELETED_ORDERS +
           SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION + SQL_CONDITION_END_TIME + endPeriodDate +
           SQL_END_CONDITION, CONDITION_SORT_EXECUTION_DATE, databaseConnection);
    }

    @Override
    public List<Order> getDeletedOrdersSortByPrice(String startPeriodDate, String endPeriodDate, DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getDeletedOrdersSortByPrice");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        LOGGER.trace("Parameter propertyFileName: {}", databaseConnection);
        return getOrdersFromDatabase(CONDITION_DELETED_ORDERS +
           SQL_CONDITION_START_TIME + startPeriodDate + SQL_END_CONDITION + SQL_CONDITION_END_TIME + endPeriodDate +
           SQL_END_CONDITION, SQL_REQUEST_SORT_BY_PRICE, databaseConnection);
    }

    @Override
    public List<Order> getMasterOrders(Master master, DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getMasterOrders");
        LOGGER.trace("Parameter master: {}", master);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        try (PreparedStatement statement = databaseConnection.getConnection()
            .prepareStatement(SQL_REQUEST_GET_MASTER_ORDERS)) {
            statement.setLong(1, master.getId());
            statement.setLong(2, master.getId());
            statement.setLong(3, master.getId());
            statement.setLong(4, master.getId());
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get master orders");
        }
    }

    @Override
    public int getNumberOrders(DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getNumberOrders");
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(SQL_REQUEST_TO_GET_NUMBER_RECORDS)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("number_orders");
            }
            return 0;
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get number orders");
        }
    }

    @Override
    public Order getOrderById(Long index, DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getOrderById");
        LOGGER.trace("Parameter index: {}", index);
        LOGGER.trace("Parameter databaseConnection: {}", databaseConnection);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(SQL_REQUEST_ORDER_BY_ID)) {
            statement.setLong(1, index);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return parseResultSet(resultSet).get(0);
            }
            return null;
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get order by id");
        }
    }

    @Override
    protected List<Order> parseResultSet(ResultSet resultSet) {
        LOGGER.debug("Method parseResultSet");
        LOGGER.trace("Parameter resultSet: {}", resultSet);
        try {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Long order_id = resultSet.getLong("orders_order_id");
                if (order_id == 0) {
                    continue;
                }
                Order order;
                boolean isNewOrder = false;
                if (orders.isEmpty() || !order_id.equals(orders.get(orders.size() - 1).getId())) {
                    order = getOrderFromResultSet(resultSet);
                    order.setId(order_id);
                    isNewOrder = true;
                } else {
                    order = orders.get(orders.size() - 1);
                }
                Master master = getMasterFromResultSet(resultSet);
                if (master.getName() != null) {
                    order.getMasters().add(master);
                }
                if (isNewOrder) {
                    orders.add(order);
                }
            }
            return orders;
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get records orders");
        }
    }

    @Override
    protected void fillStatementCreate(PreparedStatement statement, Order order) {
        LOGGER.debug("Method fillStatementCreate");
        LOGGER.trace("Parameter statement: {}", statement);
        LOGGER.trace("Parameter order: {}", order);
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
            LOGGER.error(String.valueOf(e));
            throw new DaoException("Error fill statement for create request");
        }
    }

    @Override
    protected void fillStatementUpdate(PreparedStatement statement, Order order) {
        LOGGER.debug("Method fillStatementUpdate");
        LOGGER.trace("Parameter statement: {}", statement);
        LOGGER.trace("Parameter order: {}", order);
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
            LOGGER.error(String.valueOf(e));
            throw new DaoException("Error fill statement for update request");
        }
    }

    @Override
    protected void fillStatementUpdateAll(PreparedStatement statement, Order order) {
        LOGGER.debug("Method fillStatementUpdateAll");
        LOGGER.trace("Parameter statement: {}", statement);
        LOGGER.trace("Parameter order: {}", order);
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
            LOGGER.error(String.valueOf(e));
            throw new DaoException("Error fill statement for update request");
        }
    }

    @Override
    protected void fillStatementDelete(PreparedStatement statement, Order order) {
        LOGGER.debug("Method fillStatementDelete");
        LOGGER.trace("Parameter statement: {}", statement);
        LOGGER.trace("Parameter order: {}", order);
        try {
            statement.setLong(1, order.getId());
        } catch (SQLException e) {
            LOGGER.error(String.valueOf(e));
            throw new DaoException("Error fill statement for update request");
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

    private List<Order> getOrdersFromDatabase(String conditionOrder, String conditionFullRequest,
                                              DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getOrdersFromDatabase");
        LOGGER.debug("Parameter conditionOrder: {}", conditionOrder);
        LOGGER.debug("Parameter conditionFullRequest: {}", conditionFullRequest);
        LOGGER.debug("Parameter databaseConnection: {}", databaseConnection);
        String request = String.format(SQL_REQUEST_TO_GET_ALL_RECORDS_CONDITION, SQL_REQUEST_TO_GET_ORDERS +
                conditionOrder, SQL_REQUEST_TO_GET_ORDERS + conditionOrder, SQL_REQUEST_TO_GET_ORDERS + conditionOrder,
                SQL_REQUEST_TO_GET_ORDERS + conditionOrder, conditionFullRequest);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get records orders");
        }
    }

    private int getIntFromRequest(String request, DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getIntFromRequest");
        LOGGER.debug("Parameter request: {}", request);
        LOGGER.debug("Parameter databaseConnection: {}", databaseConnection);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("amount_of_elements");
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get records orders");
        }
    }

    private Master getMasterFromResultSet(ResultSet resultSet) {
        LOGGER.debug("Method getMasterFromResultSet");
        LOGGER.trace("Parameter resultSet: {}", resultSet);
        try {
            Master master = new Master();
            master.setId(resultSet.getLong("id"));
            master.setName(resultSet.getString("name"));
            master.setDelete(resultSet.getBoolean("is_deleted"));
            master.setNumberOrders(resultSet.getInt("number_orders"));
            return master;
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get records order masters");
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet) {
        LOGGER.debug("Method getOrderFromResultSet");
        LOGGER.trace("Parameter resultSet: {}", resultSet);
        try {
            Order order = new Order(resultSet.getString("automaker"), resultSet.getString("model"),
                    resultSet.getString("registration_number"));
            order.setCreationTime(DateUtil.getDatesFromString(resultSet.getString("creation_time"), true));
            order.setExecutionStartTime(DateUtil.getDatesFromString(resultSet.getString("execution_start_time"),
                    true));
            order.setLeadTime(DateUtil.getDatesFromString(resultSet.getString("lead_time"), true));
            order.setPrice(resultSet.getBigDecimal("price"));
            order.setStatus(Status.valueOf(resultSet.getString("status")));
            order.setDeleteStatus(resultSet.getBoolean("orders_is_deleted"));
            Place place = new Place();
            place.setId(resultSet.getLong("places_place_id"));
            place.setNumber(resultSet.getInt("number"));
            place.setBusyStatus(resultSet.getBoolean("place_is_busy"));
            place.setDelete(resultSet.getBoolean("place_is_deleted"));
            order.setPlace(place);
            return order;
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get records order");
        }
    }
}