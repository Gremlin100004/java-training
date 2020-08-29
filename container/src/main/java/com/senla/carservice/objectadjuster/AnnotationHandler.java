package com.senla.carservice.objectadjuster;

import com.senla.carservice.contex.Context;

public interface AnnotationHandler {
    void configure(Object classInstance, Context context);
}