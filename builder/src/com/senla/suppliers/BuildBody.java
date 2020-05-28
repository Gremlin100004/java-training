package com.senla.suppliers;

import com.senla.spares.Body;
import com.senla.spares.IProductPart;

public class BuildBody implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Create part of product:");
        final Body body = new Body();
        System.out.println("The " + body.getName() + " has been created successfully!");
        return body;
    }
}
