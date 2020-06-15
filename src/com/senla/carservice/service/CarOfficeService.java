package com.senla.carservice.service;

import com.senla.carservice.domain.Order;

import java.util.ArrayList;

public interface CarOfficeService {
    int getNumberFreePlaceDate(ArrayList<Order> orders);
    int getNumberFreeMasters(ArrayList<Order> orders);
}