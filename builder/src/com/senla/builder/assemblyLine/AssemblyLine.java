package com.senla.builder.assemblyLine;

import com.senla.builder.product.IProduct;
import com.senla.builder.productPart.IProductPart;
import com.senla.builder.productPart.ProductPart;

public class AssemblyLine implements IAssemblyLine {

    private final ProductPart productPart1;
    private final ProductPart productPart2;
    private final ProductPart productPart3;

    public AssemblyLine(final IProductPart productPart1, final IProductPart productPart2, final IProductPart productPart3) {
        this.productPart1 = (ProductPart) productPart1;
        this.productPart2 = (ProductPart) productPart2;
        this.productPart3 = (ProductPart) productPart3;
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

    @Override
    public IProduct assembleProduct(IProduct iProduct) {
        iProduct.installFirstPart(this.productPart1);
        iProduct.installSecondPart(this.productPart2);
        iProduct.installThirdPart(this.productPart3);
        return iProduct;
    }
}