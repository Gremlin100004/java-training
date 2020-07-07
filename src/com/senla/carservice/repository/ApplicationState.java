package com.senla.carservice.repository;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;

import java.io.Serializable;
import java.util.List;

public class ApplicationState implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Master> masters;
    private List<Place> places;
    private List<Order> orders;
    private IdGenerator idGeneratorMaster;
    private IdGenerator idGeneratorPlace;
    private IdGenerator idGeneratorOrder;

    public ApplicationState() {
    }

    public List<Master> getMasters() {
        return masters;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public IdGenerator getIdGeneratorMaster() {
        return idGeneratorMaster;
    }

    public IdGenerator getIdGeneratorPlace() {
        return idGeneratorPlace;
    }

    public IdGenerator getIdGeneratorOrder() {
        return idGeneratorOrder;
    }

    public void setMasters(final List<Master> masters) {
        this.masters = masters;
    }

    public void setPlaces(final List<Place> places) {
        this.places = places;
    }

    public void setOrders(final List<Order> orders) {
        this.orders = orders;
    }

    public void setIdGeneratorMaster(final IdGenerator idGeneratorMaster) {
        this.idGeneratorMaster = idGeneratorMaster;
    }

    public void setIdGeneratorPlace(final IdGenerator idGeneratorPlace) {
        this.idGeneratorPlace = idGeneratorPlace;
    }

    public void setIdGeneratorOrder(final IdGenerator idGeneratorOrder) {
        this.idGeneratorOrder = idGeneratorOrder;
    }
}