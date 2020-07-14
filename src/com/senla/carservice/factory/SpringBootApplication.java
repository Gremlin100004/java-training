package com.senla.carservice.factory;

import com.senla.carservice.factory.configurator.Configurator;
import com.senla.carservice.factory.configurator.ConfiguratorImpl;

public class SpringBootApplication {
    private String projectPacage;

    public SpringBootApplication(String projectPackage) {
        this.projectPacage = projectPackage;
    }

    public Context run(){
        Configurator configurator = new ConfiguratorImpl(projectPacage);
        Builder builder = new Builder();
        Context context = new Context(configurator, builder);
        //create singleton
        return context;
    }
}
