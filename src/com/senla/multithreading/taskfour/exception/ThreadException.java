package com.senla.multithreading.taskfour.exception;

// можно для задач из ДЗ-9 создать пакет common и переиспользовать классы
public class ThreadException extends RuntimeException {
    public ThreadException(String message) {
        super(message);
    }
}