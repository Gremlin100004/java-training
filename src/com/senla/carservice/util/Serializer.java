package com.senla.carservice.util;

import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;
import com.senla.carservice.repository.OrderRepository;
import com.senla.carservice.repository.OrderRepositoryImpl;
import com.senla.carservice.repository.PlaceRepository;
import com.senla.carservice.repository.PlaceRepositoryImpl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class Serializer {
    private static final ClassLoader CLASS_LOADER = PropertyLoader.class.getClassLoader();
    private static final String MASTER_SER = PropertyLoader.getPropertyValue("serializableMaster");
    private static final String PLACE_SER = PropertyLoader.getPropertyValue("serializablePlace");
    private static final String ORDER_SER = PropertyLoader.getPropertyValue("serializableOrder");

    private Serializer() {
    }

    public static void serializeMaster(MasterRepository masterRepository) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(
            Objects.requireNonNull(CLASS_LOADER.getResource(MASTER_SER)).getFile()))) {
            objectOutputStream.writeObject(masterRepository);
        } catch (IOException e) {
            throw new BusinessException("Serialize masters problem");
        }
    }

    public static MasterRepository deserializeMaster() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(
            Objects.requireNonNull(CLASS_LOADER.getResource(MASTER_SER)).getFile()))) {
            return (MasterRepositoryImpl) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new BusinessException("Deserialize masters problem");
        }
    }

    public static void serializePlace(PlaceRepository placeRepository) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(
            Objects.requireNonNull(CLASS_LOADER.getResource(PLACE_SER)).getFile()))) {
            objectOutputStream.writeObject(placeRepository);
        } catch (IOException e) {
            throw new BusinessException("Serialize places problem");
        }
    }

    public static PlaceRepository deserializePlace() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(
            Objects.requireNonNull(CLASS_LOADER.getResource(PLACE_SER)).getFile()))) {
            return (PlaceRepositoryImpl) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new BusinessException("Deserialize places problem");
        }
    }

    public static void serializeOrder(OrderRepository orderRepository) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(
            Objects.requireNonNull(CLASS_LOADER.getResource(ORDER_SER)).getFile()))) {
            objectOutputStream.writeObject(orderRepository);
        } catch (IOException e) {
            throw new BusinessException("Serialize orders problem");
        }
    }

    public static OrderRepository deserializeOrder() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(
            Objects.requireNonNull(CLASS_LOADER.getResource(ORDER_SER)).getFile()))) {
            return (OrderRepositoryImpl) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new BusinessException("Deserialize orders problem");
        }
    }
}