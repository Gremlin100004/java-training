package com.senla.carservice.service;

import com.senla.carservice.repository.Order;

import java.util.Comparator;

public class OrderStartComparator implements Comparator<Order> {

    @Override
    public int compare(Order orderOne, Order orderTwo) {
        if (orderOne.getExecutionStartTime() == null && orderTwo.getExecutionStartTime() == null) return 0;
        if (orderOne.getExecutionStartTime() == null) return -1;
        if (orderTwo.getExecutionStartTime() == null) return 1;
        return orderOne.getExecutionStartTime().compareTo(orderTwo.getExecutionStartTime());
    }
}