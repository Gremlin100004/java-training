package com.senla.builder.service;

import com.senla.builder.suppliers.ILineStep;
import com.senla.builder.product.IProduct;

public class AssemblyLine implements IAssemblyLine {

    private final ILineStep bodyLineStep;
    private final ILineStep motherBoardLineStep;
    private final ILineStep monitorLineStep;

    public AssemblyLine(ILineStep bodyLineStep, ILineStep motherBoardLineStep, ILineStep monitorLineStep) {
        this.bodyLineStep = bodyLineStep;
        this.motherBoardLineStep = motherBoardLineStep;
        this.monitorLineStep = monitorLineStep;
    }

    @Override
    public IProduct assembleProduct(IProduct iProduct) {
        iProduct.installFirstPart(this.bodyLineStep.buildProductPart());
        iProduct.installSecondPart(this.motherBoardLineStep.buildProductPart());
        iProduct.installThirdPart(this.monitorLineStep.buildProductPart());
        return iProduct;
    }
}
