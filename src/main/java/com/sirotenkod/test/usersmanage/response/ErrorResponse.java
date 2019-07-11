package com.sirotenkod.test.usersmanage.response;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private Integer status;
    private String error;

    public ErrorResponse(HttpStatus status) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }

    public ErrorResponse(HttpStatus status, String error) {
        this.status = status.value();
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
