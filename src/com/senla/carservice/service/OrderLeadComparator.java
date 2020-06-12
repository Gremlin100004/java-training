package com.senla.carservice.service;

import com.senla.carservice.repository.Order;

import java.util.Comparator;

public class OrderLeadComparator implements Comparator<Order> {

    @Override
    public int compare(Order orderOne, Order orderTwo) {
        if (orderOne.getLeadTime() == null && orderTwo.getLeadTime() == null) return 0;
        if (orderOne.getLeadTime() == null) return -1;
        if (orderTwo.getLeadTime() == null) return 1;
        return orderOne.getLeadTime().compareTo(orderTwo.getLeadTime());
    }
}