package ru.abdullaeva.javacoursework.mappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NotSuchPageExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoSuchPageException.class)
    protected ResponseEntity<ApiException> handleNoSuchPageException() {
        return new ResponseEntity<>(new ApiException("Page с таким pageName не сущесвует"), HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class ApiException {
        private String message;
    }
}

