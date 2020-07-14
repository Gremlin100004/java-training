package com.senla.carservice.factory;

import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.factory.annotation.Prototype;
import com.senla.carservice.factory.annotation.Singleton;
import com.senla.carservice.factory.configurator.Configurator;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private Builder builder;
    private final Map<Class<?>, Object> singletonArray = new HashMap<>();
    private final Map<String, Class<?>> classesImplementationArray = new HashMap<>();
    private Configurator configurator;

    public Context(final Configurator configurator) {
        this.configurator = configurator;
    }

    private void createObjectsSingleton(){
        List<Class<?>> classes = configurator.getPackageScanner().getArrayClasses();
        for (Class<?> packageClass: classes){
            if (Arrays.asList(packageClass.getInterfaces()).size() > 1){
                throw new BusinessException("class has more then one implementation");
            }
            if (packageClass.isAnnotationPresent(Singleton.class)){
                Class<?> classInterface = packageClass.getInterfaces()[0];
                Object rawObject = createRawObject(classInterface);
                singletonArray.put(classInterface, rawObject);
            }
        }
    }


    public Object createRawObject(Class<?> implementClass) {
        try {
            return implementClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            //TODO : Add logging.
        }
        return null;
    }

    public <T> T getObject(Class<? extends T> classImplement) {

    }
}

    public <T> T getObjectSingleton(Class<? extends T> classImplement) {
        if (singletonArray.containsKey(classImplement)) {
            return (T) singletonArray.get(classImplement);
        }
        return null;
    }

    public <T> void addSingleton(Class<? extends T> implementClass, T singletonObject) {
        if (implementClass.isAnnotationPresent(Prototype.class)) {
            return;
        }
        singletonArray.put(implementClass, singletonObject);
    }

    public Configurator getConfigurator() {
        return configurator;
    }

    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> getImplementClass(Class<?>[] interfaceClass) {
        if (!interfaceClass.isInterface()) {
            return interfaceClass;
        }
        return (Class<? extends T>) packageClasses.stream()
            .filter(classProject -> Arrays.asList(classProject.getInterfaces())
                .contains(interfaceClass)).findFirst().orElse(null);
}
