package com.senla.service;

import com.senla.suppliers.ILineStep;
import com.senla.product.IProduct;

public class AssemblyLine implements IAssemblyLine {

    private final ILineStep bodyLineStep;
    private final ILineStep motherBoardLineStep;
    private final ILineStep monitorLineStep;

    public AssemblyLine(ILineStep body, ILineStep motherboard, ILineStep monitor) {
        this.bodyLineStep = body;
        this.motherBoardLineStep = motherboard;
        this.monitorLineStep = monitor;
    }

    public ILineStep getBodyLineStep() {
        return bodyLineStep;
    }

    public ILineStep getMotherBoardLineStep() {
        return motherBoardLineStep;
    }

    public ILineStep getMonitorLineStep() {
        return monitorLineStep;
    }

    @Override
    public IProduct assembleProduct(IProduct iProduct) {
        iProduct.installFirstPart(this.bodyLineStep.buildProductPart());
        iProduct.installSecondPart(this.motherBoardLineStep.buildProductPart());
        iProduct.installThirdPart(this.monitorLineStep.buildProductPart());
        return iProduct;
    }
}
