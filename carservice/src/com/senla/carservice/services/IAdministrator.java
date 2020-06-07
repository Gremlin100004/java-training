package com.senla.carservice.services;

import com.senla.carservice.domain.IOrder;

import java.util.Calendar;

public interface IAdministrator extends IMasterService, IGarageService, IOrderService {
    String getCarServiceName();

    IOrder[] getOrders();

    int getNumberFreePlaceDate(Calendar date);

    int getNumberFreeMasters(Calendar date);

    Calendar getNearestFreeDate();
}