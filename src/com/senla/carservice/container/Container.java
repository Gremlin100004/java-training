package com.senla.carservice.container;

import com.senla.carservice.container.annotationhandler.DependencyInjectionAnnotationHandler;
import com.senla.carservice.container.annotationhandler.PropertyDependencyAnnotationHandler;
import com.senla.carservice.container.configurator.Configurator;
import com.senla.carservice.util.PropertyLoader;

public class Container {
    private final String projectPackage;
    private Context context;

    public Container(String projectPackage) {
        this.projectPackage = projectPackage;
        initialization();
    }

    public Container() {
        this.projectPackage = PropertyLoader.getPropertyValue(DefaultValue.PACKAGE_PROJECT.toString());
        initialization();
    }

    private void initialization(){
        Configurator configurator = new Configurator(projectPackage);
        Context context = new Context(configurator);
        context.setPropertyDependency(new PropertyDependencyAnnotationHandler());
        context.setDependencyInjection(new DependencyInjectionAnnotationHandler(context));
        this.context = context;
    }

    public Object getObject(Class<?> inputClass){
        return context.getObject(inputClass.getName());
    }
}
