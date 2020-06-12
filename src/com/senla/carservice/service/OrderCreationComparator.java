package com.senla.carservice.service;

import com.senla.carservice.repository.Order;

import java.util.Comparator;

public class OrderCreationComparator implements Comparator<Order> {

    @Override
    public int compare(Order orderOne, Order orderTwo) {
        if (orderOne.getCreationTime() == null && orderTwo.getCreationTime() == null) return 0;
        if (orderOne.getCreationTime() == null) return -1;
        if (orderTwo.getCreationTime() == null) return 1;
        return orderOne.getCreationTime().compareTo(orderTwo.getCreationTime());
    }
}
