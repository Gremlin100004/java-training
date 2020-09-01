package com.senla.carservice.objectadjuster.propertyinjection.enumeration;

public enum DefaultValue {

    PROPERTY_FILE_NAME("application.properties");
    private String value;

    DefaultValue(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}