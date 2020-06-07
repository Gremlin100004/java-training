package com.senla.carservice;

import com.senla.carservice.controller.CarServiceModel;

public class Main {
    public static void main(String[] args) {
        CarServiceModel carServiceModel = new CarServiceModel();
        carServiceModel.init();
        carServiceModel.addMaster();
        carServiceModel.deleteMaster();
        carServiceModel.getMasterByAlphabet();
        carServiceModel.AddGarage();
        carServiceModel.deleteGarage();
        carServiceModel.addGaragePlace();
        carServiceModel.deleteGaragePlace();
        carServiceModel.addOrder();
        carServiceModel.completeOrder();
        carServiceModel.getFreePlaceGarage();
        carServiceModel.closeOrder();
        carServiceModel.cancelOrder();
        carServiceModel.deleteOrder();
        carServiceModel.shiftLeadTime();
        carServiceModel.getOrderByCreationTime();
        carServiceModel.getOrderByLeadTime();
        carServiceModel.getOrderByStartTime();
        carServiceModel.getOrderByPrice();
        carServiceModel.getExecutedOrderByCreationTime();
        carServiceModel.getExecutedOrderByLeadTime();
        carServiceModel.getExecutedOrderByPrice();
        carServiceModel.getMasterOrder();
        carServiceModel.getOrderMasters();
        carServiceModel.getCompletedOrders();
        carServiceModel.getCanceledOrders();
        carServiceModel.getDeletedOrders();
        carServiceModel.getFreePlacesByDate();
        carServiceModel.getNearestFreeDate();
    }
}