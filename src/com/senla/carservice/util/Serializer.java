package com.senla.carservice.util;

import com.senla.carservice.repository.ApplicationState;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class Serializer {
    private static final ClassLoader CLASS_LOADER = PropertyLoader.class.getClassLoader();
    private static final String PATH_SER = PropertyLoader.getPropertyValue("serialize.entities.filePath");

    private Serializer() {
    }

    public static void serializeEntities(ApplicationState applicationState) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(
            Objects.requireNonNull(CLASS_LOADER.getResource(PATH_SER)).getFile()))) {
            objectOutputStream.writeObject(applicationState);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO : Add logging.
        }
    }

    public static ApplicationState deserializeEntities() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(
            Objects.requireNonNull(CLASS_LOADER.getResource(PATH_SER)).getFile()))) {
            return (ApplicationState) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO : Add logging.
        }
        return null;
    }
}