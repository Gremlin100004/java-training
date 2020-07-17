package com.senla.carservice.container;

import com.senla.carservice.container.annotationhandler.DependencyInjectionAnnotationHandler;
import com.senla.carservice.container.annotationhandler.PropertyDependencyAnnotationHandler;
import com.senla.carservice.container.configurator.Configurator;
import com.senla.carservice.container.contex.Context;
import com.senla.carservice.enumeration.DefaultValue;
import com.senla.carservice.util.PropertyLoader;

public class Container {
    private Context context;

    public Container(String projectPackage) {
        initialization(projectPackage);
    }

    public Container() {
        // модуль инъекции получил зависимость от проекта
        initialization(PropertyLoader.getPropertyValue(DefaultValue.PROPERTY_FILE_NAME.toString(),
                                                       DefaultValue.PACKAGE_PROJECT.toString()));
    }

    // нейминг
    private void initialization(String projectPackage) {
        Configurator configurator = new Configurator(projectPackage);
        // что мешает новый объект сразу сохранить в поле класса?
        Context context = new Context(configurator);
        context.setPropertyDependency(new PropertyDependencyAnnotationHandler());
        context.setDependencyInjection(new DependencyInjectionAnnotationHandler(context));
        context.createSingletons();
        context.configureSingletons();
        this.context = context;
    }

    // не совсем правильное использование дженериков - на вход должен приходить класс Class<T> objectClass
    @SuppressWarnings("unchecked")
    public <T> T getObject(String inputClass) {
        return (T) context.getObject(inputClass);
    }
}