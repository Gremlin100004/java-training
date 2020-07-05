package com.senla.carservice.configuration;

import com.senla.carservice.annotation.Singleton;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContex {
    private Builder builder;
    private Map<Class, Object> cache = new HashMap<>();
    private Config config;

    private ApplicationContex(final Config config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> type){
        if (cache.containsKey(type)) {
            return (T) cache.get();
        }
        Class<? extends T> implementClass = type;
        if (type.isInterface()) {
            implementClass = config.getImplementClass(type);
        }
        T object = builder.createObject(implementClass);
        if(implementClass.isAnnotationPresent(Singleton.class)){
            cache.put(type, implementClass);
        }
    }
}
