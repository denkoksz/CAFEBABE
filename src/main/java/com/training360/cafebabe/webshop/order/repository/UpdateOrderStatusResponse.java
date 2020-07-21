package com.training360.cafebabe.webshop.order.repository;

public class UpdateOrderStatusResponse {

    private boolean success;
    private String message;


    public UpdateOrderStatusResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public UpdateOrderStatusResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {

        return success;
    }

    public String getMessage() {
        return message;
    }
}
