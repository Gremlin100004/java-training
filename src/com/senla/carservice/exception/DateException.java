package com.senla.carservice.exception;

// я проверил все места, где ловится это исключение - оно везде ловится в одном блоке с бизнес исключением
// тогда какой смысл выделать отдельно это исключение? используй бизнес исключение везде, просто с
// правильным сообщением
public class DateException extends RuntimeException {
    public DateException(String message) {
        super(message);
    }
}