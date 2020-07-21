// опечатки в коде затрудняют поиск
package com.senla.carservice.container.enumaration;

// наверное, не очень удачное имя для класса - если я правильно понимаю, он хранит не проперти велью
// а проперти key - ключи для значенией
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