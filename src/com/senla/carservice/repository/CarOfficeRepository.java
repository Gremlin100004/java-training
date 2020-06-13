package com.senla.carservice.repository;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.util.IdGenerator;

public interface CarOfficeRepository {

    IdGenerator getIdGeneratorGarage();

    IdGenerator getIdGeneratorOrder();

    IdGenerator getIdGeneratorMaster();

    String getName();

    Order[] getOrders();

    Master[] getMasters();

    Garage[] getGarages();

    void setName(String name);

    void setOrders(Order[] orders);

    void setMasters(Master[] masters);

    void setGarages(Garage[] garages);
}
