package com.training360.cafebabe.webshop.basket.service;

public class BasketOperationResponse {

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public BasketOperationResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public BasketOperationResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
