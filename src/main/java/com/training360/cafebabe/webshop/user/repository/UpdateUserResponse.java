package com.training360.cafebabe.webshop.user.repository;

public class UpdateUserResponse {

    private boolean success;

    private String message;

    public UpdateUserResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
