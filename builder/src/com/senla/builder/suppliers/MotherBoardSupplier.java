package com.senla.builder.suppliers;

import com.senla.builder.spares.IProductPart;
import com.senla.builder.spares.MotherBoard;

public class MotherBoardSupplier implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Create part of product:");
        final MotherBoard monitor = new MotherBoard();
        System.out.println("The " + monitor.toString() + " has been created successfully!");
        return monitor;
    }
}
