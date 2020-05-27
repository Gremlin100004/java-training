package com.senla.builder.product;

import com.senla.builder.productPart.IProductPart;
import com.senla.builder.productPart.ProductPart;

public class Product implements IProduct {
    private String name;
    private ProductPart productPart1;
    private ProductPart productPart2;
    private ProductPart productPart3;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ProductPart getProductPart1() {
        return productPart1;
    }

    public ProductPart getProductPart2() {
        return productPart2;
    }

    public ProductPart getProductPart3() {
        return productPart3;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductPart1(ProductPart productPart1) {
        this.productPart1 = productPart1;
    }

    public void setProductPart2(ProductPart productPart2) {
        this.productPart2 = productPart2;
    }

    public void setProductPart3(ProductPart productPart3) {
        this.productPart3 = productPart3;
    }

    @Override
    public void installFirstPart(IProductPart iProductPart) {
        System.out.println("Instalation the first part of product:");
        final ProductPart productPart1 = (ProductPart) iProductPart;
        this.productPart1 = productPart1;
        System.out.println("The " + productPart1.getName() + " has been installed successfully!");
    }

    @Override
    public void installSecondPart(IProductPart iProductPart) {
        System.out.println("Instalation the second part of product:");
        final ProductPart productPart2 = (ProductPart) iProductPart;
        this.productPart2 = productPart2;
        System.out.println("The " + productPart2.getName() + " has been installed successfully!");
    }

    @Override
    public void installThirdPart(IProductPart iProductPart) {
        System.out.println("Instalation the third part of product:");
        final ProductPart productPart3 = (ProductPart) iProductPart;
        this.productPart3 = productPart3;
        System.out.println("The " + productPart3.getName() + " has been installed successfully!");
    }
}