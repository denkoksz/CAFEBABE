package com.training360.cafebabe.webshop.user.service;

public class UserNameAvailableResponse {
    private boolean available;

    public UserNameAvailableResponse(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }
}
