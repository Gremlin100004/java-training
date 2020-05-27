package com.senla.builder.lineStep;

import com.senla.builder.productPart.IProductPart;
import com.senla.builder.productPart.ProductPart;

public class LineStep implements ILineStep {
    String namePartProduct;

    public LineStep(String namePartProduct) {
        this.namePartProduct = namePartProduct;
    }

    public String getNamePartProduct() {
        return namePartProduct;
    }

    public void setNamePartProduct(String namePartProduct) {
        this.namePartProduct = namePartProduct;
    }

    @Override
    public IProductPart buildProductPart() {
        System.out.println("Create part of product:");
        ProductPart productPart = new ProductPart(this.namePartProduct);
        System.out.println("The " + productPart.getName() + " has been created successfully!");
        return productPart;
    }
}