package com.senla.carservice.controller.exception;

import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.exception.DateException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DaoException.class)
    protected ResponseEntity<ErrorMessage> handleDaoException(DaoException daoException, WebRequest request) {
        return new ResponseEntity<>(new ErrorMessage(daoException.getMessage()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorMessage> handleBusinessException(BusinessException businessException) {
        return new ResponseEntity<>(new ErrorMessage(businessException.getMessage()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DateException.class)
    protected ResponseEntity<ErrorMessage> handleDateException(DateException dateException) {
        return new ResponseEntity<>(new ErrorMessage(dateException.getMessage()), HttpStatus.CONFLICT);
    }
    @Getter
    @AllArgsConstructor
    private static class ErrorMessage {
        private final String message;
    }
}