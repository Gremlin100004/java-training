package com.senla.carservice.container;

import com.senla.carservice.container.configurator.Configurator;
import com.senla.carservice.container.contex.Context;
import com.senla.carservice.container.enumeration.PropertyKey;
import com.senla.carservice.container.property.PropertyLoader;

public class Container {
    private static Context context;

    static {
        initialize(PropertyLoader.getPropertyValue(PropertyKey.PROPERTY_FILE_NAME.getValue(),
                                                   PropertyKey.PACKAGE_PROJECT.getValue()));
    }

    private static void initialize(String projectPackage) {
        Configurator configurator = new Configurator(projectPackage);
        context = new Context(configurator);
        context.createAnnotationHandlers(context);
        context.createSingletons();
        context.configureSingletons();
    }

    public static <T> T getObject(Class<T> objectClass) {
        return context.getObject(objectClass);
    }
}