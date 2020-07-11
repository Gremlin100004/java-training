package com.senla.carservice.factory.customizer;

public interface ObjectCustomizer {
    <O> O configure(O inputObject);
}