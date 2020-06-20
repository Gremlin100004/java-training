package com.senla.carservice.service;

import com.senla.carservice.domain.Order;

import java.util.List;

public interface CarOfficeService {
    int getNumberFreePlaceDate(List<Order> orders);

    int getNumberFreeMasters(List<Order> orders);
}