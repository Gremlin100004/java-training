package com.senla.builder.product;

import com.senla.builder.spares.IProductPart;

public interface IProduct {
    void installFirstPart(IProductPart iProductPart);
    void installSecondPart(IProductPart iProductPart);
    void installThirdPart(IProductPart iProductPart);
}
