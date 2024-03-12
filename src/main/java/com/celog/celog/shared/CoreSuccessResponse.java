package com.celog.celog.shared;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoreSuccessResponse {
    public static CoreSuccessResponse coreSuccessResponse(
            Object data,
            String message,
            int httpStatus
    ) {
        return CoreSuccessResponse.builder()
                .ok(true)
                .message(message)
                .httpStatus(httpStatus)
                .data(data)
                .build();
    }

    private boolean ok;
    private int httpStatus;
    private String message;
    private Object data;
}
