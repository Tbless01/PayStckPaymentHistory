package com.example.paymentIntegration.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
    private Object data;
    private HttpStatus httpStatus;
    private int statusCode;
    private boolean isSuccessful;

    public static ApiResponse successResponse(Object data) {
        return ApiResponse.builder()
                .data(data)
                .httpStatus(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
    }

    public static ApiResponse errorResponse(String errorMessage) {
        return ApiResponse.builder()
                .data(errorMessage)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .isSuccessful(false)
                .build();
    }

}
