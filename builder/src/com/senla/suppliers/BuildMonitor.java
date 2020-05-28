package com.senla.suppliers;

import com.senla.spares.IProductPart;
import com.senla.spares.Monitor;

public class BuildMonitor implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Create part of product:");
        final Monitor monitor = new Monitor();
        System.out.println("The " + monitor.getName() + " has been created successfully!");
        return monitor;
    }
}
