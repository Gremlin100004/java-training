package com.senla.carservice.controller.exception;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.exception.DateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ClientMessageDto> handleBusinessException(BusinessException businessException) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", businessException.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(businessException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ControllerException.class)
    protected ResponseEntity<ClientMessageDto> handleControllerException(ControllerException controllerException) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", controllerException.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(controllerException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateException.class)
    protected ResponseEntity<ClientMessageDto> handleDateException(DateException dateException) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", dateException.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(dateException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException exception,
                                                                      HttpHeaders headers,
                                                                      HttpStatus status,
                                                                      WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException exception,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException exception,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException exception,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException exception,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException exception,
                                                         HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exception,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException exception,
                                                                        HttpHeaders headers,
                                                                        HttpStatus status,
                                                                        WebRequest webRequest) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        log.debug("[handleBusinessException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

}
