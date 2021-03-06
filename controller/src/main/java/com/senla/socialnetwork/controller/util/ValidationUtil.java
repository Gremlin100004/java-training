package com.senla.socialnetwork.controller.util;

import com.senla.socialnetwork.controller.exception.ControllerException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
public final class ValidationUtil {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidationUtil() {
    }

    public static void validate(final Object object) {
        Set<ConstraintViolation<Object>> violations = VALIDATOR.validate(object);
        for (ConstraintViolation<Object> violation : violations) {
            log.error(violation.getMessage());
            throw new ControllerException(violation.getMessage());
        }
    }

}
