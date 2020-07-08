package com.senla.carservice.configuration;

import java.lang.reflect.InvocationTargetException;

public class CreatorImpl implements Creator {

    @Override
    public <T> T createRawObject(Class<? extends T> implementClass) {
        try {
            return implementClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            //TODO : Add logging.
        }
        return null;
    }
}