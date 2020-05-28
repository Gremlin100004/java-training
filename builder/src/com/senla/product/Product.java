package com.senla.product;

import com.senla.spares.Body;
import com.senla.spares.IProductPart;
import com.senla.spares.Monitor;
import com.senla.spares.MotherBoard;

public class Product implements IProduct {
    private String name;
    private Body body;
    private MotherBoard motherBoard;
    private Monitor monitor;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Body getBody() {
        return body;
    }

    public MotherBoard getMotherBoard() {
        return motherBoard;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setMotherBoard(MotherBoard motherBoard) {
        this.motherBoard = motherBoard;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void installFirstPart(IProductPart iProductPart) {

        System.out.println("Installation the first part of product:");
        final Body body = (Body) iProductPart;
        this.body = body;
        System.out.println("The " + body.getName() + " has been installed successfully!");
    }

    @Override
    public void installSecondPart(IProductPart iProductPart) {
        System.out.println("Installation the second part of product:");
        final MotherBoard motherBoard = (MotherBoard) iProductPart;
        this.motherBoard = motherBoard;
        System.out.println("The " + motherBoard.getName() + " has been installed successfully!");
    }

    @Override
    public void installThirdPart(IProductPart iProductPart) {
        System.out.println("Installation the third part of product:");
        final Monitor monitor = (Monitor) iProductPart;
        this.monitor = monitor;
        System.out.println("The " + monitor.getName() + " has been installed successfully!");
    }
}
