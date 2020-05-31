package com.senla.builder.suppliers;

import com.senla.builder.spares.IProductPart;
import com.senla.builder.spares.Monitor;

public class MonitorSupplier implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Create part of product:");
        final Monitor monitor = new Monitor();
        System.out.println("The " + monitor.toString() + " has been created successfully!");
        return monitor;
    }
}
