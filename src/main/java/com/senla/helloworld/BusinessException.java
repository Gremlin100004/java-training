package com.senla.helloworld;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}