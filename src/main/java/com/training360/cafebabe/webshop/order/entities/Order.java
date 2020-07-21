package com.training360.cafebabe.webshop.order.entities;

import com.training360.cafebabe.webshop.product.entities.Product;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private Long id;
    private String userName;
    private LocalDateTime dateTime;
    private List<Product> products;
    private int sum;
    private OrderStatus status;

    public Order(Long id, String userName, LocalDateTime dateTime, List<Product> products, int sum, OrderStatus status) {
        this.id = id;
        this.userName = userName;
        this.dateTime = dateTime;
        this.products = products;
        this.sum = sum;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getSum() {
        return sum;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", dateTime=" + dateTime +
                ", products=" + products +
                ", sum=" + sum +
                ", status=" + status +
                '}';
    }
}
