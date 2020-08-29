package com.senla.carservice.property;

import com.senla.carservice.exception.InitializationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private PropertyLoader() {
    }

    public static String getPropertyValue(String propertyFileName, String propertyName) {
        ClassLoader classLoader = PropertyLoader.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(propertyFileName)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty(propertyName);
        } catch (IOException e) {
            throw new InitializationException("load resource problem");
        }
    }
}