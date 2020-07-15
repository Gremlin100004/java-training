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
        initialization(PropertyLoader.getPropertyValue(DefaultValue.PROPERTY_FILE_NAME.toString(),
                                                       DefaultValue.PACKAGE_PROJECT.toString()));
    }

    private void initialization(String projectPackage) {
        Configurator configurator = new Configurator(projectPackage);
        Context context = new Context(configurator);
        context.setPropertyDependency(new PropertyDependencyAnnotationHandler());
        context.setDependencyInjection(new DependencyInjectionAnnotationHandler(context));
        context.createSingletons();
        context.configureSingletons();
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(String inputClass) {
        return (T) context.getObject(inputClass);
    }
}