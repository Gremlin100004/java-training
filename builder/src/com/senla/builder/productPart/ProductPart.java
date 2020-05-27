package com.senla.builder.productPart;

public class ProductPart implements IProductPart {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductPart(String name) {
        this.name = name;
    }
}