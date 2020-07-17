package com.senla.carservice.util;

import com.senla.carservice.enumeration.DefaultValue;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.ApplicationState;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class Serializer {
    // для простановки ВСЕХ настроек использовать механизм из ДЗ-8
    // или убираешь настройки из пропертей и оставляешь только те, что требовались в ДЗ-7
    // или (лучше) работаешь со всем настройками в проекте одинаковым способом
    private static final String FILE_PATH_SERIALIZE = PropertyLoader
        .getPropertyValue(DefaultValue.PROPERTY_FILE_NAME.toString(), DefaultValue.FILE_PATH_SERIALIZE.toString());

    private Serializer() {
    }

    public static void serializeEntities(ApplicationState applicationState) {
        ClassLoader classLoader = Serializer.class.getClassLoader();
        URL url = classLoader.getResource(FILE_PATH_SERIALIZE);
        if (url == null) {
            throw new BusinessException("Url is null");
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
            new FileOutputStream(new File(url.toURI())))) {
            objectOutputStream.writeObject(applicationState);
        } catch (IOException | URISyntaxException e) {
            throw new BusinessException("Error url serialize object");
        }
    }

    public static ApplicationState deserializeEntities() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(
            Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_PATH_SERIALIZE))) {
            return (ApplicationState) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new BusinessException("Error deserialize objects");
        }
    }
}