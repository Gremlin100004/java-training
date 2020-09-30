//package com.senla.carservice.controller.exception;
//
//import com.senla.carservice.service.exception.BusinessException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//@Slf4j
//public class ExceptionController {
//    @ExceptionHandler(BusinessException.class)
//    private ResponseEntity<ErrorMessage> handleBadRequest(final BusinessException e) {
//        log.log(Level.SEVERE, e.getMessage(), e);
//        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), e.getHttpStatus());
//    }
//
//    @Data
//    public static class ErrorMessage {
//        private final String errorMessage;
//    }
//}
