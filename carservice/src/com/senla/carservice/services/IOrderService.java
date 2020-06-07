package com.senla.carservice.services;

import com.senla.carservice.domain.IGarage;
import com.senla.carservice.domain.IMaster;
import com.senla.carservice.domain.IOrder;
import com.senla.carservice.domain.IPlace;

import java.math.BigDecimal;
import java.util.Calendar;

public interface IOrderService {
    IOrder[] getOrders();

    void addOrder(Calendar executionStartTime,
                  Calendar leadTime,
                  IMaster[] masters,
                  IGarage garage,
                  IPlace place,
                  String automaker,
                  String model,
                  String registrationNumber, BigDecimal price);

    boolean completeOrder(IOrder order);

    boolean cancelOrder(IOrder order);

    boolean closeOrder(IOrder order);

    boolean deleteOrder(IOrder order);

    boolean shiftLeadTime(IOrder order, Calendar executionStartTime,
                          Calendar leadTime);

    IOrder[] sortOrderCreationTime(IOrder[] order);

    IOrder[] sortOrderByLeadTime(IOrder[] order);

    IOrder[] sortOrderByStartTime(IOrder[] order);

    IOrder[] sortOrderByPrice(IOrder[] order);

    IOrder[] sortOrderByPeriod(IOrder[] orders, Calendar startPeriod, Calendar EndPeriod);

    IOrder[] getCurrentRunningOrders();

    IOrder[] getMasterOrders(IMaster master);

    IMaster[] getOrderMasters(IOrder order);

    IOrder[] getCompletedOrders();

    IOrder[] getCanceledOrders();

    IOrder[] getDeletedOrders();
}