package com.api.api_gateway.gatewayAPIs;

import org.springframework.http.HttpStatus;

public class CustomError extends RuntimeException
{
    private HttpStatus status;
    private String message;

    public CustomError() {}

    public CustomError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
