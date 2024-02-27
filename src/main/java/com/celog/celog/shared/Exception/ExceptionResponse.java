package com.celog.celog.shared.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private Boolean ok;
    private String message;
    private int statusCode;
}
