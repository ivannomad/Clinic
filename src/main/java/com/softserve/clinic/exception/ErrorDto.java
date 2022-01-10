package com.softserve.clinic.exception;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ErrorDto implements Serializable {

    private final String timestamp;
    private final Integer status;
    private final String error;
    private final String message;
    private final String path;

}
