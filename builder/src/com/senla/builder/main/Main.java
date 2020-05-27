package com.senla.builder.main;

import com.senla.builder.assemblyLine.AssemblyLine;
import com.senla.builder.assemblyLine.IAssemblyLine;
import com.senla.builder.lineStep.ILineStep;
import com.senla.builder.lineStep.LineStep;
import com.senla.builder.product.Product;

public class Main {

    public static void main(String[] args) {
        String bodyName = "Body";
        String motherBoardName = "Mother Board";
        String monitorName = "Monitor";
        Product laptop = new Product("Laptop");
        System.out.println("Start build product " + laptop.getName());
        final ILineStep bodyLineStep = new LineStep(bodyName);
        final ILineStep motherBoardLineStep = new LineStep(motherBoardName);
        final ILineStep monitorLineStep = new LineStep(monitorName);
        final IAssemblyLine assemblyLine = new AssemblyLine(bodyLineStep.buildProductPart(), motherBoardLineStep.buildProductPart(), monitorLineStep.buildProductPart());
        assemblyLine.assembleProduct(laptop);
        System.out.println( "The " + laptop.getName() + "has been built successfully!");
        System.out.println( "****************************");
        System.out.println( "The " + laptop.getName() + " has:");
        System.out.println(laptop.getProductPart1().getName());
        System.out.println(laptop.getProductPart2().getName());
        System.out.println(laptop.getProductPart3().getName());
    }
}