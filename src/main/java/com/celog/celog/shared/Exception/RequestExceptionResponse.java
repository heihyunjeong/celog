package com.celog.celog.shared.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class RequestExceptionResponse {
    private Boolean ok;
    private List<Map<String,String>> message;
    private int statusCode;
}

