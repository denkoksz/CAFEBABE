package com.training360.cafebabe.webshop.user.repository;

public class CreateUserResponse {

    private boolean success;

    private String message;

    public CreateUserResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
