package com.senla.carservice;

import com.senla.carservice.configurator.Configurator;
import com.senla.carservice.contex.Context;
import com.senla.carservice.enumeration.PropertyKey;
import com.senla.carservice.exception.InitializationException;
import com.senla.carservice.property.PropertyLoader;

public class Container {
    private static Context context;

    static {
        String projectPackage = PropertyLoader
            .getPropertyValue(PropertyKey.PROPERTY_FILE_NAME.getValue(), PropertyKey.PACKAGE_PROJECT.getValue());
        if (projectPackage == null){
            throw new InitializationException("Wrong value package name");
        }
        initialize(projectPackage);
    }

    private static void initialize(String projectPackage) {
        Configurator configurator = new Configurator(projectPackage);
        context = new Context(configurator);
        context.createAnnotationHandlers();
        context.createSingletons();
        context.configureSingletons();
    }

    public static <T> T getObject(Class<T> objectClass) {
        return context.getObject(objectClass);
    }
}