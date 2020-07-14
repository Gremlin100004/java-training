package com.senla.carservice.factory.configurator;

public class Configurator {
    private PackageScanner packageScanner;

    public Configurator(String packageName) {
        packageScanner = new PackageScanner(packageName);
    }

    public PackageScanner getPackageScanner() {
        return packageScanner;
    }
}