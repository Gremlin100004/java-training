package com.senla.carservice.container.objectadjuster;

import com.senla.carservice.container.contex.Context;

public interface AnnotationHandler {
    void configure(Object classInstance, Context context);
}