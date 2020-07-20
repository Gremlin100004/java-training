package com.senla.carservice.container.propertyinjection.enumaration;

public enum DefaultValue {
    PROPERTY_FILE_NAME("application.properties");
    private String value;

    DefaultValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
