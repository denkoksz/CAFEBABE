package com.training360.cafebabe.webshop.product.service;

public class ProductOperationResponse {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ProductOperationResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public ProductOperationResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
