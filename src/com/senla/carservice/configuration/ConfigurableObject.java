package com.senla.carservice.configuration;

public interface ConfigurableObject {
   <O> O configure(O inputObject);
}