package com.senla.carservice.container;

public enum DefaultValue {
    PACKAGE_PROJECT("carservice.source.package");

    private String value;

    DefaultValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
