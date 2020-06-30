package com.senla.carservice.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
    private static final String PATH = "src/resources/config.properties";

    private PropertyUtil() {
    }

    public static String getPropertyValue(String propertyName) {
        Properties properties = new Properties();
        try ( FileInputStream in = new FileInputStream(PATH)) {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(propertyName);
    }
}