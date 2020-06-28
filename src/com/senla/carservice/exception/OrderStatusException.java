package com.senla.carservice.exception;

import com.senla.carservice.domain.Status;

import java.util.Date;

public class OrderStatusException extends Exception {
    private Status status;

    public Status getStatus() {
        return status;
    }

    public OrderStatusException(String message, Status status) {
        super(message);
        this.status = status;
    }
}
