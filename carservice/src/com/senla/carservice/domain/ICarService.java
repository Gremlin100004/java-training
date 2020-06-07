package com.senla.carservice.domain;

public interface ICarService {
    String getName();

    IOrder[] getOrders();

    IMaster[] getMasters();

    IGarage[] getGarages();

    void setOrders(IOrder[] orders);

    void setMasters(IMaster[] masters);

    void setGarages(IGarage[] garages);
}