package com.senla.builder.service;

import com.senla.builder.product.IProduct;

public interface IAssemblyLine {
    IProduct assembleProduct(IProduct iProduct);
}
