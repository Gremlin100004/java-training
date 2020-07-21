package com.senla.carservice.container.property;

import com.senla.carservice.exception.BusinessException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private PropertyLoader() {
    }

    public static String getPropertyValue(String propertyFileName, String propertyName) {
        ClassLoader classLoader = PropertyLoader.class.getClassLoader();
        // не экономь буквы, уж stream и то понятней, чем in
        try (InputStream in = classLoader.getResourceAsStream(propertyFileName)) {
            Properties properties = new Properties();
            properties.load(in);
            return properties.getProperty(propertyName);
        } catch (IOException e) {
            throw new BusinessException("load resource problem");
        }
    }
}