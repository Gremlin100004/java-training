package com.senla.carservice.container.configurator;

import com.senla.carservice.container.annotation.Prototype;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.exception.BusinessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configurator {
    private final PackageScanner packageScanner;
    private final Map<String, Class<?>> prototypeClasses = new HashMap<>();
    private final List<Class<?>> singletonClasses = new ArrayList<>();

    public Configurator(String packageName) {
        packageScanner = new PackageScanner(packageName);
        initialize();
    }

    // метод называется "дай классы", но возвращает один класс
    public Class<?> getPrototypeClasses(String className) {
        return prototypeClasses.get(className);
    }

    public List<Class<?>> getSingletonClasses() {
        return singletonClasses;
    }

    private void initialize() {
        List<Class<?>> classesOfPackage = packageScanner.getArrayClasses();
        if (classesOfPackage.isEmpty()) {
            throw new BusinessException("there are no classes");
        }
        // сканируешь пакеты второй раз
        for (Class<?> classPackage : packageScanner.getArrayClasses()) {
            // чтобы посчитать количество элементов в массиве, нужно обязательно перевести его в
            // лист? может, есть еще способы?
            if (Arrays.asList(classPackage.getInterfaces()).size() > 1) {
                // иметь много интерфейсов нельзя в твоем приложении? это не очень удобно
                throw new BusinessException("class has more then one interface");
            }
            if (classPackage.isAnnotationPresent(Singleton.class)) {
                singletonClasses.add(classPackage);
            } else if (classPackage.isAnnotationPresent(Prototype.class)) {
                prototypeClasses.put(classPackage.getName(), classPackage);
            }
        }
    }
}