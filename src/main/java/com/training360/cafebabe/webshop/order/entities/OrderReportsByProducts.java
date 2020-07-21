package com.training360.cafebabe.webshop.order.entities;

public class OrderReportsByProducts {

    private String date;

    private String productName;

    private int countOfProducts;

    private int price;

    private int sum;

    public OrderReportsByProducts(String date, String productName, int countOfProducts, int price, int sum) {
        this.date = date;
        this.productName = productName;
        this.countOfProducts = countOfProducts;
        this.price = price;
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public String getProductName() {
        return productName;
    }

    public int getCountOfProducts() {
        return countOfProducts;
    }

    public int getPrice() {
        return price;
    }

    public int getSum() {
        return sum;
    }
}
