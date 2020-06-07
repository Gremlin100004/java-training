package com.senla.carservice.domain;

public class Master implements IMaster {
    private String name;
    private int numberOrder;
    private boolean busyStatus = false;

    public Master(String name) {
        this.name = name;
        this.numberOrder = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOrder() {
        return numberOrder;
    }

    @Override
    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }
}