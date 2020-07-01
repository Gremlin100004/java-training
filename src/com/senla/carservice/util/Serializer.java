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

public class Serializer {
    private static final String MASTER_SER = "resources/bin/master.bin";
    private static final String PLACE_SER = "resources/bin/place.bin";
    private static final String ORDER_SER = "resources/bin/order.bin";
    
    private Serializer() {
    }

    public static void serializeMaster(MasterRepository masterRepository) {

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(MASTER_SER))) {
            objectOutputStream.writeObject(masterRepository);
        } catch (IOException e) {
            throw new BusinessException("Serialize masters problem");
        }
    }

    public static MasterRepository deserializeMaster() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(MASTER_SER))) {
            return  (MasterRepositoryImpl) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new BusinessException("Deserialize masters problem");
        }
    }

    public static void serializePlace(PlaceRepository placeRepository) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(PLACE_SER))) {
            objectOutputStream.writeObject(placeRepository);
        } catch (IOException e) {
            throw new BusinessException("Serialize places problem");
        }
    }

    public static PlaceRepository deserializePlace() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(PLACE_SER))) {
            return (PlaceRepositoryImpl) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new BusinessException("Deserialize places problem");
        }
    }

    public static void serializeOrder(OrderRepository orderRepository)  {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(ORDER_SER))){
            objectOutputStream.writeObject(orderRepository);
        } catch (IOException e) {
            throw new BusinessException("Serialize orders problem");
        }
    }

    public static OrderRepository deserializeOrder() {

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(ORDER_SER))) {
            return  (OrderRepositoryImpl) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new BusinessException("Deserialize orders problem");
        }
    }
}