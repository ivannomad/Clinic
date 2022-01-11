package com.softserve.clinic.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> entityNotFoundExceptionHandler(HttpServletRequest request, EntityNotFoundException exception) {
        return getResponseEntity(request, exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> internalServerErrorHandler(HttpServletRequest request, Exception exception) {
        return getResponseEntity(request, exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMostSpecificCause().getMessage();
        String path = ((ServletWebRequest) request).getRequest().getRequestURL().toString();
        log.error("Exception raised = {} :: URL = {}", message, path);
        ErrorDto errorDto = ErrorDto.builder()
                    .status(status.value())
                    .error(status.getReasonPhrase())
                    .message(message)
                    .path(path)
                    .build();
        return new ResponseEntity<>(errorDto, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, status);
    }

    private ResponseEntity<ErrorDto> getResponseEntity(HttpServletRequest request, Exception exception, HttpStatus httpStatus) {
        log.error("Exception raised = {} :: URL = {}", exception.getMessage(), request.getRequestURL());
        ErrorDto errorDto = ErrorDto.builder()
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(exception.getMessage())
                .path(request.getRequestURL().toString())
                .build();
        return new ResponseEntity<>(errorDto, httpStatus);
    }

}
