package com.senla.carservice.repository;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.util.IdGenerator;

import java.util.ArrayList;

public interface CarOfficeRepository {

    IdGenerator getIdGeneratorGarage();

    IdGenerator getIdGeneratorOrder();

    IdGenerator getIdGeneratorMaster();

    ArrayList<Order> getOrders();

    ArrayList<Master> getMasters();

    ArrayList<Garage> getGarages();

    void setOrders(ArrayList<Order> orders);

    void setMasters(ArrayList<Master> masters);

    void setGarages(ArrayList<Garage> garages);
}