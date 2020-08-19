package com.senla.carservice.dao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.Date;
import java.util.List;

public interface OrderDao extends GenericDao {

    Order getLastOrder();

    int getNumberFreeMasters(String startPeriod, String endPeriod);

    int getNumberFreePlaces(String startPeriod, String endPeriod);

    List<Master> getOrderMasters(Order order);

    void createRecordTableOrdersMasters(Order order, Master master);
}