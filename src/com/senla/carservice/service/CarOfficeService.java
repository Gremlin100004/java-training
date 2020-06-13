package com.senla.carservice.service;

import com.senla.carservice.domain.Order;

public interface CarOfficeService {
    int getNumberFreePlaceDate(Order[] orders);
    int getNumberFreeMasters(Order[] orders);
}
