package com.training360.cafebabe.webshop.product.repository;

import com.training360.cafebabe.webshop.product.entities.Product;

//alapvető esetben feltételezzük hogy nem talált a query megfelelő rekordot, ekkor simán vissza tudunk adni
//egy new ProductResponse-t, amennyiben van találat, setPresent(true), setProduct(product)
public class ProductResponse {
    private boolean present = false;
    private Product product = null;

    public boolean getPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
