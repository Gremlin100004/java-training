package com.senla.carservice.configuration;

import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;

import java.io.ObjectInputFilter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Builder {
    private static final Builder INSTANCE = new Builder();
    private List<ConfigurableObject> configurators = new ArrayList<>();
    private Config config;

    private Builder() {
        config = new ConfigImpl("com.senla.carservice", new HashMap<>(Map.of(MasterRepository.class,
                                                                             MasterRepositoryImpl.class)));
    }



    public static Builder getInstance(){
        return INSTANCE;
    }

    public <T> T createObject(Class<T> implementClass) {

        try {
            return implementClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        T t = implementClass.getDeclaredConstructor().newInstance();

        configurators.forEach(configurableObject -> configurableObject.configure(t));
        return t;
    }



}
