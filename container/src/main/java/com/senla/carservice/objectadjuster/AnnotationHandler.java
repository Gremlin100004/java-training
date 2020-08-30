package com.senla.carservice.objectadjuster;

import com.senla.carservice.context.Context;

public interface AnnotationHandler {
    void configure(Object classInstance, Context context);
}