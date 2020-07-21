package com.training360.cafebabe.webshop.basket.entities;

import com.training360.cafebabe.webshop.product.entities.Product;

import java.util.List;

public class Basket {

    private int sum;
    private List<Product> products;

    public Basket(int sum, List<Product> products) {
        this.sum = sum;
        this.products = products;
    }

    public int getSum() {
        return sum;
    }

    public List<Product> getProducts() {
        return products;
    }
}
