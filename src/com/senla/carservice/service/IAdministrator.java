package com.senla.carservice.service;

import com.senla.carservice.repository.Order;

import java.util.Date;

public interface IAdministrator extends IMasterService, IGarageService, IOrderService {
    String getCarServiceName();

    Order[] getOrders();

    int getNumberFreePlaceDate(Date date);

    int getNumberFreeMasters(Date date);

    Date getNearestFreeDate();
}