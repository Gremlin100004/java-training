package com.senla.carservice.factory.creator;

import java.lang.reflect.InvocationTargetException;

// отдельный пакет, класс с интерфейсом только для того, чтобы создавать объект из предоставленного класса?
// причем используется лишь единожды в одном классе в одном метода
// хватило бы приватного метода
public class CreatorImpl implements Creator {

    @Override
    public <T> T createRawObject(Class<? extends T> implementClass) {
        try {
            return implementClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            //TODO : Add logging.
        }
        // эта строка выполнится только в случае, если сработает кэтч - почему бы ее не поместить туда?
        return null;
    }
}