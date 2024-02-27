package com.celog.celog.shared;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoreSuccessResponse {
    private boolean ok;
    private int httpStatus;
    private String message;
    private Object data;
}
