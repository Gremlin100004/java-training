package com.senla.builder.assemblyLine;

import com.senla.builder.product.IProduct;

public interface IAssemblyLine {
    public IProduct assembleProduct(IProduct iProduct);
}