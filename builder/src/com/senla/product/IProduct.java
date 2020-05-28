package com.senla.product;

import com.senla.spares.IProductPart;

public interface IProduct {
    void installFirstPart(IProductPart iProductPart);
    void installSecondPart(IProductPart iProductPart);
    void installThirdPart(IProductPart iProductPart);
}
