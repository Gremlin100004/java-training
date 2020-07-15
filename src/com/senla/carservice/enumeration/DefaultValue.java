package com.senla.carservice.enumeration;

public enum DefaultValue {
    PACKAGE_PROJECT("carservice.source.package"),
    PROPERTY_FILE_NAME("application.properties"),
    FILE_PATH_SERIALIZE("serialize.entities.filePath"),
    PROPERTY_MASTER_CSV_FILE_PATH("csv.master.pathFile"),
    PROPERTY_ORDER_CSV_FILE_PATH("csv.order.pathFile"),
    PROPERTY_PLACE_CSV_FILE_PATH("csv.place.pathFile"),
    PROPERTY_MASTER_FIELD_SEPARATOR("csv.separator.field"),
    PROPERTY_MASTER_ID_SEPARATOR("csv.separator.id");

    private String value;

    DefaultValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}