package com.senla.carservice.exception;

import java.util.Date;

public class DateException extends Exception {
    private Date dateOne;
    private Date dateTwo;

    public Date getDateOne() {
        return dateOne;
    }

    public Date getDateTwo() {
        return dateTwo;
    }

    public DateException(String message, Date dateOne, Date dateTwo) {
        super(message);
        this.dateOne = dateOne;
        this.dateTwo = dateTwo;
    }
}