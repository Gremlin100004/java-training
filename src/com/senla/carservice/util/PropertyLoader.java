package com.senla.carservice.util;

import com.senla.carservice.exception.BusinessException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static final String PATH = "application.properties";

    private PropertyLoader() {
    }

    public static String getPropertyValue(String propertyName) {
        ClassLoader classLoader = PropertyLoader.class.getClassLoader();
        Properties properties = new Properties();
        try (InputStream in = classLoader.getResourceAsStream(PATH)) {
            properties.load(in);
        } catch (IOException e) {
            throw new BusinessException("load resource problem");
        }
        return properties.getProperty(propertyName);
    }
}