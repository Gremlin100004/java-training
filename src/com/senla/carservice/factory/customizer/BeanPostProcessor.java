package com.senla.carservice.factory.customizer;

import com.senla.carservice.factory.Context;

public interface BeanPostProcessor {
    <O> O configure(O inputObject, Context context);
}