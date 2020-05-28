package com.senla.suppliers;

import com.senla.spares.IProductPart;
import com.senla.spares.MotherBoard;

public class BuildMotherBoard implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Create part of product:");
        final MotherBoard monitor = new MotherBoard();
        System.out.println("The " + monitor.getName() + " has been created successfully!");
        return monitor;
    }
}
