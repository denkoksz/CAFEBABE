package com.training360.cafebabe.webshop.product.entities;

public class Product {
    private long id;
    private String productKey;
    private String name;
    private String url;
    private String manufacturer;
    private int price;
    private boolean isActive;

    public Product(long id, String productKey, String name, String url, String manufacturer, int price, boolean isActive) {
        if (price <= 0 || isEmpty(productKey, name, url, manufacturer)) {
            //TODO: hibadobálás?
        }
        this.id = id;
        this.productKey = productKey;
        this.name = name;
        this.url = url;
        this.manufacturer = manufacturer;
        this.price = price;
        this.isActive = isActive;
    }

    public String getProductKey() {
        return productKey;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getPrice() {
        return price;
    }

    private boolean isEmpty(String... strs) {
        for (String str : strs) {
            if (str == null || str.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public long getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id= " + id +", "+
                "name= "+ name;

    }
}
