package com.senla.carservice.service;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.NumberObjectZeroException;

import java.util.List;

public interface CarOfficeService {
    String getNearestFreeDate() throws NumberObjectZeroException;
}