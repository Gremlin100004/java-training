package com.senla.carservice.util;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.ApplicationState;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;

@Singleton
public class Serializer {
    @ConfigProperty
    private String filePathSerialize;

    public Serializer() {
    }

    public void serializeEntities(ApplicationState applicationState) {
        ClassLoader classLoader = Serializer.class.getClassLoader();
        URL url = classLoader.getResource(filePathSerialize);
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

    public ApplicationState deserializeEntities() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(
            Thread.currentThread().getContextClassLoader().getResourceAsStream(filePathSerialize))) {
            return (ApplicationState) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new BusinessException("Error deserialize objects");
        }
    }
}