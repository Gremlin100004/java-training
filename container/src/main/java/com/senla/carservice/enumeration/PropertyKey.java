package com.senla.carservice.enumeration;

public enum PropertyKey {

    PACKAGE_PROJECT("carservice.source.package"),
    PROPERTY_FILE_NAME("application.properties");

    private final String value;

    PropertyKey(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}