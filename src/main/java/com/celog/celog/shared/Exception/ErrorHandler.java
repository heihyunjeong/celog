package com.celog.celog.shared.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
        System.out.println("MethodArgumentNotValidException"+e.getBindingResult());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<Map<String, String>> errors = fieldErrors.stream()
                .map(error -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("field", error.getField());
                    errorMap.put("message", Objects.requireNonNull(error.getDefaultMessage()));
                    return errorMap;
                })
                .collect(Collectors.toList());

        RequestExceptionResponse exceptionResponse = new RequestExceptionResponse(
                false,
                errors,
                e.getStatusCode().value()
        );
        return new ResponseEntity<>(exceptionResponse, e.getStatusCode());
    }

    // HttpExceptionCustom 예외를 처리하는 핸들러를 추가합니다.
    @ExceptionHandler(HttpExceptionCustom.class)
    public ResponseEntity<ExceptionResponse> handleHttpExceptionCustom(HttpExceptionCustom e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                e.isOk(),    // 이 부분은 'false'로 고정될 것 같으니, e.isOk() 대신 false를 직접 사용할 수도 있습니다.
                e.getMessage(),
                e.getHttpStatus().value()
        );

        return new ResponseEntity<>(exceptionResponse, e.getHttpStatus());
    }
}
