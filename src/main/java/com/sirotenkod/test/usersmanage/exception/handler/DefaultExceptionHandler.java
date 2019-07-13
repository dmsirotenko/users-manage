package com.sirotenkod.test.usersmanage.exception.handler;

import com.sirotenkod.test.usersmanage.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class DefaultExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) throws Exception {
        // If status is annotated, throw to spring handler
        if (statusAnnotated(ex.getClass()) || ex instanceof ResponseStatusException) {
            throw ex;
        }

        logger.error("Error while processing request", ex);

        return handleExceptionInternal();
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal() {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(new ErrorResponse(status), status);
    }

    private Boolean statusAnnotated(Class<?> exceptionClass) {
        return AnnotationUtils.findAnnotation(
                exceptionClass, ResponseStatus.class) != null;
    }
}
