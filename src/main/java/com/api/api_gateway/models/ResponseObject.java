package com.api.api_gateway.models;

import org.springframework.http.HttpStatus;

public class ResponseObject<T>
{
    private final T messageBody;
    private final HttpStatus status;

    public ResponseObject(T messageBody, HttpStatus status) {
        this.messageBody = messageBody;
        this.status = status;
    }

    public T getMessageBody() {
        return messageBody;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
