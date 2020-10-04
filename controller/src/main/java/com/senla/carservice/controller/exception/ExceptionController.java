package com.senla.carservice.controller.exception;

import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.exception.DateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DaoException.class)
    protected ResponseEntity<ClientMessageDto> handleDaoException(DaoException daoException, WebRequest request) {
        return new ResponseEntity<>(new ClientMessageDto(daoException.getMessage()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ClientMessageDto> handleBusinessException(BusinessException businessException) {
        return new ResponseEntity<>(new ClientMessageDto(businessException.getMessage()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DateException.class)
    protected ResponseEntity<ClientMessageDto> handleDateException(DateException dateException) {
        return new ResponseEntity<>(new ClientMessageDto(dateException.getMessage()), HttpStatus.CONFLICT);
    }
}