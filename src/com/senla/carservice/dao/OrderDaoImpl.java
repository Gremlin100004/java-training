package com.senla.carservice.dao;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.BusinessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends AbstractDao implements OrderDao{
    private static final String SQL_REQUEST_TO_ADD_RECORD = "INSERT INTO masters VALUES (NULL";

    @Override
    @SuppressWarnings("unchecked")
    protected List<Order> parseResultSet(ResultSet resultSet) {
        try {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order(resultSet.getLong("id"), resultSet.getString("automaker"),
                        resultSet.getString("model"), resultSet.getString("registration_number"));
//                order.setCreationTime();
//                order.setExecutionStartTime();
//                order.setLeadTime();
//                order.setLeadTime();
                orders.add(order);
            }
            return orders;
        } catch (SQLException ex) {
            throw new BusinessException("Error request get record masters");
        }
    }

    @Override
    protected <T> String getCreateRequest(T object) {
        return null;
    }

    @Override
    protected String getReadAllRequest() {
        return null;
    }

    @Override
    protected <T> String getUpdateRequest(T object) {
        return null;
    }

    @Override
    protected <T> String getDeleteRequest(T object) {
        return null;
    }
}