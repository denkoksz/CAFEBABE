package com.training360.cafebabe.webshop.order.controller;

public class OrderOperationResponse {

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public OrderOperationResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public OrderOperationResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}

