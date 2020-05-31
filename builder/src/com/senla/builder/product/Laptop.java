package com.senla.builder.product;

import com.senla.builder.spares.Body;
import com.senla.builder.spares.IProductPart;
import com.senla.builder.spares.Monitor;
import com.senla.builder.spares.MotherBoard;

public class Laptop implements IProduct {
    private IProductPart body;
    private IProductPart motherBoard;
    private IProductPart monitor;
    public Laptop() {}

    @Override
    public String toString() {
        return "Laptop";
    }

    @Override
    public void installFirstPart(IProductPart iProductPart) {
        System.out.println("Installation the first part of product:");
        body = iProductPart;
        System.out.println("The " + body.toString() + " has been installed successfully!");
    }

    @Override
    public void installSecondPart(IProductPart iProductPart) {
        System.out.println("Installation the second part of product:");
        motherBoard = iProductPart;
        System.out.println("The " + motherBoard.toString() + " has been installed successfully!");
    }

    @Override
    public void installThirdPart(IProductPart iProductPart) {
        System.out.println("Installation the third part of product:");
        monitor  = iProductPart;
        System.out.println("The " + monitor.toString() + " has been installed successfully!");
    }
}
