package com.example.paymentIntegration.utils;

import org.springframework.http.HttpStatus;

public class GenerateApiResponse {

    public static final String INVALID_CREDENTIALS = "You entered invalid credentials, please try again" ;
    public static final String BEARER ="Bearer ";

    public static ApiResponse createdResponse(Object data){
        return ApiResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .isSuccessful(true)
                .data(data)
                .build();
    }

    public static ApiResponse okResponse(Object data) {
        return ApiResponse.builder()
                .data(data)
                .httpStatus(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
    }


    public static ApiResponse BadResponse(Object data) {
        return ApiResponse.builder()
                .data(data)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .isSuccessful(false)
                .build();
    }
}
