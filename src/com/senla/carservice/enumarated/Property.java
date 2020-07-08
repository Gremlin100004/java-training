package com.senla.carservice.enumarated;

public enum Property {
    SOURCE_FOLDER("src"),
    PACKAGE_PROJECT("com.senla.carservice")

    ;


    private String value;

    Property(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
