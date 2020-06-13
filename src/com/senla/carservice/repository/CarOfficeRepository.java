package com.senla.carservice.repository;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.util.IdGenerator;

public interface CarOfficeRepository {

    IdGenerator getIdGeneratorGarage();

    IdGenerator getIdGeneratorOrder();

    IdGenerator getIdGeneratorMaster();

    Order[] getOrders();

    Master[] getMasters();

    Garage[] getGarages();

    void setOrders(Order[] orders);

    void setMasters(Master[] masters);

    void setGarages(Garage[] garages);
}
