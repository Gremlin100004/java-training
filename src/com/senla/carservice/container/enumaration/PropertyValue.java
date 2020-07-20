package com.senla.carservice.container.enumaration;

public enum PropertyValue {
    PACKAGE_PROJECT("carservice.source.package"),
    PROPERTY_FILE_NAME("application.properties");

    private String value;

    PropertyValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}