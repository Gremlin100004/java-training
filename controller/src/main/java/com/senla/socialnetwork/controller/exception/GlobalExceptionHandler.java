package com.senla.socialnetwork.controller.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.service.exception.BusinessException;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ClientMessageDto> handleBusinessException(final BusinessException exception) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ControllerException.class)
    protected ResponseEntity<ClientMessageDto> handleControllerException(ControllerException exception) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    protected ResponseEntity<ClientMessageDto> handleAuthenticationCredentialsNotFoundException(final AuthenticationCredentialsNotFoundException exception) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Error, you are not logged in"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ClientMessageDto> handleAccessDeniedException(final AccessDeniedException exception) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Error, you do not have access rights"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ClientMessageDto> handleAuthenticationException(final AuthenticationException exception) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Login or password error"), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException exception,
                                                                         final HttpHeaders headers,
                                                                         final HttpStatus status,
                                                                         final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Method not supported"), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException exception,
                                                                     final HttpHeaders headers,
                                                                     final HttpStatus status,
                                                                     final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("MediaType not supported"), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(final HttpMediaTypeNotAcceptableException exception,
                                                                      final HttpHeaders headers,
                                                                      final HttpStatus status,
                                                                      final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("MediaType not acceptable"), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(final MissingPathVariableException exception,
                                                               final HttpHeaders headers,
                                                               final HttpStatus status,
                                                               final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Missing path variable"), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException exception,
                                                                          final HttpHeaders headers,
                                                                          final HttpStatus status,
                                                                          final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Missing request parameter"), status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(final ServletRequestBindingException exception,
                                                                          final HttpHeaders headers,
                                                                          final HttpStatus status,
                                                                          final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Request binding error"), status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(final ConversionNotSupportedException exception,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Conversion not supported"), status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException exception,
                                                        final HttpHeaders headers,
                                                        final HttpStatus status,
                                                        final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("The entered data is incorrect"), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException exception,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("The message cannot be read"), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(final HttpMessageNotWritableException exception,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Message not available for writing"), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("The entered data is incorrect"), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException exception,
                                                                     final HttpHeaders headers,
                                                                     final HttpStatus status,
                                                                     final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("The request error"), status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException exception,
                                                         final HttpHeaders headers,
                                                         final HttpStatus status,
                                                         final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("The request error"), status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException exception,
                                                                   final HttpHeaders headers,
                                                                   final HttpStatus status,
                                                                   final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("The request error"), status);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(final AsyncRequestTimeoutException exception,
                                                                        final HttpHeaders headers,
                                                                        final HttpStatus status,
                                                                        final WebRequest webRequest) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Async request timeout"), status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception exception, Object body,
                                                             final HttpHeaders headers,
                                                             final HttpStatus status,
                                                             final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("The request error"), status);
    }

    @Override
    public void commence(final HttpServletRequest httpServletRequest,
                         final HttpServletResponse httpServletResponse,
                         final AuthenticationException authException) {
        try {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(org.apache.http.HttpStatus.SC_FORBIDDEN);
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(
                new ClientMessageDto("Permission denied")));
        } catch (IOException exception) {
            log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
            throw new ControllerException("Response error");
        }
    }

    @Override
    public void handle(final HttpServletRequest httpServletRequest,
                       final HttpServletResponse httpServletResponse,
                       final AccessDeniedException deniedException) {
        try {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(org.apache.http.HttpStatus.SC_FORBIDDEN);
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(
                new ClientMessageDto("Authorisation error")));
        } catch (IOException exception) {
            log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
            throw new ControllerException("Response error");
        }
    }
}
