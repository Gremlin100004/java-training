package com.senla.carservice.container;

import com.senla.carservice.container.configurator.Configurator;
import com.senla.carservice.container.contex.Context;
import com.senla.carservice.container.dependencyinjection.annotationhandler.DependencyInjectionAnnotationHandler;
import com.senla.carservice.container.enumaration.PropertyValue;
import com.senla.carservice.container.property.PropertyLoader;
import com.senla.carservice.container.propertyinjection.annotationhandler.PropertyDependencyAnnotationHandler;

public class Container {
    private static Context context;

    static {
        initialize(PropertyLoader.getPropertyValue(PropertyValue.PROPERTY_FILE_NAME.toString(),
                                                   PropertyValue.PACKAGE_PROJECT.toString()));
    }

    private static void initialize(String projectPackage) {
        Configurator configurator = new Configurator(projectPackage);
        context = new Context(configurator);
        // я наконец понял твою задумку с интерфейсом для конфигураторов
        // посмотрел видос Борисова, думаю, ты тоже его смотрел
        // в целом, жалею, что попросил тебя убрать - задумка хорошая, просто выглядело
        // уж очень запутанно
        context.setPropertyDependency(new PropertyDependencyAnnotationHandler());
        context.setDependencyInjection(new DependencyInjectionAnnotationHandler(context));
        context.createSingletons();
        context.configureSingletons();
    }

    public static <T> T getObject(Class<T> objectClass) {
        return context.getObject(objectClass);
    }
}