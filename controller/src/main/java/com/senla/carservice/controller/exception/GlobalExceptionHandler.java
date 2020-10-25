package com.senla.carservice.controller.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.exception.DateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

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

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    protected ResponseEntity<ClientMessageDto> handleAuthenticationException(AuthenticationCredentialsNotFoundException authenticationCredentialsNotFoundException) {
        log.debug("[handleAuthenticationException]");
        log.error("[{}]", authenticationCredentialsNotFoundException.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Error, you are not logged in"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ClientMessageDto> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        log.debug("[handleAccessDeniedException]");
        log.error("[{}]", accessDeniedException.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Error, you do not have access rights"), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        log.debug("[handleHttpRequestMethodNotSupported]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        log.debug("[handleHttpMediaTypeNotSupported]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException exception,
                                                                      HttpHeaders headers,
                                                                      HttpStatus status,
                                                                      WebRequest request) {
        log.debug("[handleHttpMediaTypeNotAcceptable]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException exception,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        log.debug("[handleMissingPathVariable]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        log.debug("[handleMissingServletRequestParameter]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException exception,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        log.debug("[handleServletRequestBindingException]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.debug("[handleConversionNotSupported]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException exception,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {
        log.debug("[handleTypeMismatch]");
        log.error("[{}]", exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.debug("[handleHttpMessageNotReadable]");
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

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) {
        log.debug("[commence]");
        try {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(org.apache.http.HttpStatus.SC_FORBIDDEN);
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new ClientMessageDto("Permission denied")));
        } catch (IOException exception) {
            log.error("[{}]", exception.getMessage());
            throw new ControllerException("Response error");
        }
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) {
        try {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(org.apache.http.HttpStatus.SC_FORBIDDEN);
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(
                new ClientMessageDto("Authorisation error")));
        } catch (IOException exception) {
            log.error("[{}]", exception.getMessage());
            throw new ControllerException("Response error");
        }
    }
}
