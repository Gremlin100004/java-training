package com.senla.carservice.util;

import com.senla.carservice.repository.ApplicationState;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class Serializer {
    private static final String PATH_SER = PropertyLoader.getPropertyValue("serialize.entities.filePath");

    private Serializer() {
    }

    public static void serializeEntities(ApplicationState applicationState) {
        ClassLoader classLoader = Serializer.class.getClassLoader();
        URL url = classLoader.getResource(PATH_SER);
        if (url == null){
            //TODO : Add logging.
            return;
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
            new FileOutputStream(new File(url.toURI())))) {
            objectOutputStream.writeObject(applicationState);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            //TODO : Add logging.
        }
    }

    public static ApplicationState deserializeEntities() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(
            Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH_SER))) {
            return (ApplicationState) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
            //TODO : Add logging.
        }
    }
}