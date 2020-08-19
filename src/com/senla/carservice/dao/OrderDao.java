package com.senla.carservice.dao;

import com.senla.carservice.domain.Order;

public interface OrderDao extends GenericDao {

    Order getLastOrder();
}