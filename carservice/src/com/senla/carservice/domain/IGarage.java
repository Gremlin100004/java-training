package com.senla.carservice.domain;

public interface IGarage {
    String getName();

    IPlace[] getPlaces();

    void setName(String name);

    void setPlaces(IPlace[] places);
}