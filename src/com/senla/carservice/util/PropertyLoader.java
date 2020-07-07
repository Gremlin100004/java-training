package com.senla.carservice.util;

import com.senla.carservice.exception.BusinessException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class PropertyLoader {
    private static final String PATH = "application.properties";

    private PropertyLoader() {
    }

    public static String getPropertyValue(String propertyName) {
        ClassLoader classLoader = PropertyLoader.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(PATH)).getFile());
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream(file)) {
            properties.load(in);
        } catch (IOException e) {
            throw new BusinessException("load resource problem");
        }
        return properties.getProperty(propertyName);
    }
}