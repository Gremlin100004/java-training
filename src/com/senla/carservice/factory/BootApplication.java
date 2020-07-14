package com.senla.carservice.factory;

import com.senla.carservice.factory.configurator.Configurator;

public class BootApplication {
    private String projectPackage;

    public BootApplication(String projectPackage) {
        this.projectPackage = projectPackage;
    }

    public Context run(){
        Configurator configurator = new Configurator(projectPackage);
        Context context = new Context(configurator);
        Builder builder = new Builder();

        //create singleton
        return context;
    }
}
