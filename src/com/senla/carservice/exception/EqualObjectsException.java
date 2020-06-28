package com.senla.carservice.exception;

public class EqualObjectsException extends Exception {
    private Object objectOne;
    private Object objectTwo;

    public Object getObjectOne() {
        return objectOne;
    }

    public Object getObjectTwo() {
        return objectTwo;
    }

    public EqualObjectsException(String message, Object objectOne, Object objectTwo) {
        super(message);
        this.objectOne = objectOne;
        this.objectTwo = objectTwo;
    }
}
