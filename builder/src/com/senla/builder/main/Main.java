package com.senla.builder.main;

import com.senla.builder.service.AssemblyLine;
import com.senla.builder.service.IAssemblyLine;
import com.senla.builder.suppliers.ILineStep;
import com.senla.builder.suppliers.BodySupplier;
import com.senla.builder.suppliers.MonitorSupplier;
import com.senla.builder.suppliers.MotherBoardSupplier;
import com.senla.builder.product.Laptop;
import com.senla.builder.product.IProduct;

public class Main {

    public static void main(String[] args) {
        IProduct laptop = new Laptop();
        System.out.println("Start build product " + laptop.toString());
        final ILineStep bodyLineStep = new BodySupplier();
        final ILineStep motherBoardLineStep = new MotherBoardSupplier();
        final ILineStep monitorLineStep = new MonitorSupplier();
        final IAssemblyLine assemblyLine = new AssemblyLine(bodyLineStep, motherBoardLineStep, monitorLineStep);
        laptop = assemblyLine.assembleProduct(laptop);
        System.out.println( "The " + laptop.toString() + " has been built successfully!");
    }
}
