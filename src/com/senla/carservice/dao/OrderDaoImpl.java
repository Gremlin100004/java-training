package com.senla.carservice.dao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.List;

public class OrderDaoImpl implements OrderDao{
    @Override
    public Order getLastOrder() {
        return null;
    }

    @Override
    public List<Order> getCompletedOrders() {
        return null;
    }

    @Override
    public List<Order> getDeletedOrders() {
        return null;
    }

    @Override
    public List<Order> getCanceledOrders() {
        return null;
    }

    @Override
    public List<Order> getRunningOrders() {
        return null;
    }

    @Override
    public List<Order> getMasterOrders(final Master master) {
        return null;
    }

    @Override
    public List<Master> getOrderMasters(final Order order) {
        return null;
    }

    @Override
    public void createRecord(final Object object) {
    }

    @Override
    public List<Object> getAllRecords() {
        return null;
    }

    @Override
    public void updateRecord(Object object) {
    }

    @Override
    public void updateAllRecords(Object object) {
    }

    @Override
    public void deleteRecord(Object object) {
    }
}