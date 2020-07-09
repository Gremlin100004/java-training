package com.senla.carservice.test;

import com.senla.carservice.factory.annotation.Property;
import com.senla.carservice.factory.annotation.Prototype;

@Prototype
public class TestObject {
    @Property("carservice.test.TestObject.value")
    private String value;

    public TestObject() {
    }

    public TestObject(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}