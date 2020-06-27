package com.senla.carservice.exception;

import java.util.Date;

public class NumberObjectZeroException extends Exception{
    private int number;

    public int getNumber() {
        return number;
    }

    public NumberObjectZeroException(String message, int number) {
        super(message);
        this.number = number;
    }
}