package com.senla.carservice.container.propertyinjection.enumaration;

public enum TypeField {
    SHORT(Short.class),
    LONG(Long.class),
    STRING(String.class),
    BOOLEAN(Boolean.class),
    INTEGER(Integer.class),
    DOUBLE(Double.class),
    DEFAULT(String.class);
    private Class<?> aClass;

    TypeField(final Class<?> aClass) {
        this.aClass = aClass;
    }

    // нейминг
    public Class<?> getaClass() {
        return aClass;
    }
}



