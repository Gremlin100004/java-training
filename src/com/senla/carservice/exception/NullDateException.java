package com.senla.carservice.exception;

import java.util.Date;

public class NullDateException extends Exception {
    private Date date;

    public Date getDate() {
        return date;
    }

    public NullDateException(String message, Date date) {
        super(message);
        this.date = date;
    }
}