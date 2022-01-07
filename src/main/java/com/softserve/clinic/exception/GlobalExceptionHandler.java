package com.softserve.clinic.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NullEntityReferenceException.class)
    public ResponseEntity<ErrorDto> nullEntityReferenceExceptionHandler(HttpServletRequest request, NullEntityReferenceException exception) {
        return getResponseEntity(request, exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> entityNotFoundExceptionHandler(HttpServletRequest request, EntityNotFoundException exception) {
        return getResponseEntity(request, exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> internalServerErrorHandler(HttpServletRequest request, Exception exception) {
        return getResponseEntity(request, exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorDto> getResponseEntity(HttpServletRequest request, Exception exception, HttpStatus httpStatus) {
        log.error("Exception raised = {} :: URL = {}", exception.getMessage(), request.getRequestURL());
        ErrorDto errorDto = ErrorDto.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(exception.getMessage())
                .path(request.getRequestURL().toString())
                .build();
        return new ResponseEntity<>(errorDto, httpStatus);
    }

}
