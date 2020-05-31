package com.senla.builder.suppliers;

import com.senla.builder.spares.Body;
import com.senla.builder.spares.IProductPart;

public class BodySupplier implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Create part of product:");
        final Body body = new Body();
        System.out.println("The " + body.toString() + " has been created successfully!");
        return body;
    }
}
