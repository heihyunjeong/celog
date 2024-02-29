package com.celog.celog.shared.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
public class HttpExceptionCustom extends RuntimeException {
    private boolean ok;
    private String message;
    private HttpStatus httpStatus;
}