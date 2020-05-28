package com.senla.main;

import com.senla.service.AssemblyLine;
import com.senla.service.IAssemblyLine;
import com.senla.suppliers.ILineStep;
import com.senla.suppliers.BuildBody;
import com.senla.suppliers.BuildMonitor;
import com.senla.suppliers.BuildMotherBoard;
import com.senla.product.Product;
import com.senla.product.IProduct;

public class Main {

    public static void main(String[] args) {
        IProduct laptop = new Product("Laptop");
        System.out.println("Start build product " + ((Product) laptop).getName());
        final ILineStep bodyLineStep = new BuildBody();
        final ILineStep motherBoardLineStep = new BuildMotherBoard();
        final ILineStep monitorLineStep = new BuildMonitor();
        final IAssemblyLine assemblyLine = new AssemblyLine(bodyLineStep, motherBoardLineStep, monitorLineStep);
        laptop = assemblyLine.assembleProduct(laptop);
        System.out.println( "The " + ((Product) laptop).getName() + " has been built successfully!");
        System.out.println( "****************************");
        System.out.println( "The " + ((Product) laptop).getName() + " has:");
        System.out.println(((Product) laptop).getBody().getName());
        System.out.println(((Product) laptop).getMotherBoard().getName());
        System.out.println(((Product) laptop).getMonitor().getName());
    }
}
