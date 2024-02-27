package com.celog.celog.shared.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터 값을 확인해주세요."),

    //404 NOT_FOUND 잘못된 리소스 접근
    DISPLAY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 ID 입니다."),
    FAIR_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 박람회 ID 박람회입니다."),
    SAVED_DISPLAY_NOT_FOUND(HttpStatus.NOT_FOUND, "저장하지 않은 데이터입니다."),
    //409 CONFLICT 중복된 리소스
    ALREADY_SAVED_USER(HttpStatus.CONFLICT, "이미 저장한 전시회입니다."),
    ALREADY_SAVED_FAIR(HttpStatus.CONFLICT, "이미 저장한 박람회입니다."),
    ALREADY_SAVED_FESTIVAL(HttpStatus.CONFLICT, "이미 저장한 페스티벌입니다."),

    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

    private final HttpStatus status;
    private final String message;
}
