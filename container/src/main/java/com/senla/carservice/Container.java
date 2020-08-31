package com.senla.carservice;

import com.senla.carservice.configurator.Configurator;
import com.senla.carservice.context.Context;
import com.senla.carservice.enumeration.PropertyKey;
import com.senla.carservice.exception.InitializationException;
import com.senla.carservice.property.PropertyLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Container {

    private static Context context;
    private static final Logger LOGGER = LoggerFactory.getLogger(Container.class);

    static {
        LOGGER.debug("initialize class Container");
        String projectPackage = PropertyLoader
            .getPropertyValue(PropertyKey.PROPERTY_FILE_NAME.getValue(), PropertyKey.PACKAGE_PROJECT.getValue());
        if (projectPackage == null) {
            LOGGER.error("Wrong value package name");
            throw new InitializationException("Wrong value package name");
        }
        initialize(projectPackage);
    }

    private static void initialize(String projectPackage) {
        LOGGER.info("Start initialize app");
        LOGGER.info("Create configurator, scan package");
        Configurator configurator = new Configurator(projectPackage);
        LOGGER.info("Create Context");
        context = new Context(configurator);
        LOGGER.info("Create annotation handlers");
        context.createAnnotationHandlers();
        LOGGER.info("Create all classes which are singletons");
        context.createSingletons();
        LOGGER.info("Configure all classes singletons");
        context.configureSingletons();
        LOGGER.info("App initialize successfully");
    }

    public static <T> T getObject(Class<T> objectClass) {
        return context.getObject(objectClass);
    }
}