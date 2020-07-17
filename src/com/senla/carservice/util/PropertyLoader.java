package com.senla.carservice.util;

import com.senla.carservice.exception.BusinessException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private PropertyLoader() {
    }

    public static String getPropertyValue(String propertyFileName, String propertyName) {
        // лучше не оборачивать в трай одну строку, в трай должен быть полноценный блок кода, чтобы
        // не пришлось при чтении мысленно прыгать по всем блокам и собирать логику по кусочкам
        // кроме того, нет смысла создавать объект пропертей, если ресурс не будет прочитан из-за исключения
        // поэтому создание объекта всегда где можно надо выполнять после возможного выброса исключения
        // я отрефакторю код, чтобы было понятно, о чем я:
        ClassLoader classLoader = PropertyLoader.class.getClassLoader();
        try (InputStream in = classLoader.getResourceAsStream(propertyFileName)) {
            Properties properties = new Properties();
            properties.load(in);
            return properties.getProperty(propertyName);
        } catch (IOException e) {
            throw new BusinessException("load resource problem");
        }
    }
}