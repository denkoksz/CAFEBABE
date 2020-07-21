package com.training360.cafebabe.webshop.dashboard.controller;

public class DashboardResponse {
    private int numberOfActiveProducts;
    private int numberOfProducts;
    private int numberOfUsers;
    private int numberOfOrders;
    private int numberOfActiveOrders;

    public DashboardResponse setNumberOfActiveProducts(int numberOfActiveProducts) {
        this.numberOfActiveProducts = numberOfActiveProducts;
        return this;
    }

    public DashboardResponse setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
        return this;
    }

    public DashboardResponse setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
        return this;
    }

    public DashboardResponse setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
        return this;
    }

    public DashboardResponse setNumberOfActiveOrders(int numberOfActiveOrders) {
        this.numberOfActiveOrders = numberOfActiveOrders;
        return this;
    }

    public int getNumberOfActiveProducts() {
        return numberOfActiveProducts;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public int getNumberOfActiveOrders() {
        return numberOfActiveOrders;
    }
}
