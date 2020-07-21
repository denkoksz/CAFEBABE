package com.training360.cafebabe.webshop.product.service;

import com.training360.cafebabe.webshop.product.controller.ProductRequest;
import com.training360.cafebabe.webshop.product.repository.ProductRepository;

public class ProductValidator {
    private ProductRepository repository;

    public ProductValidator(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductOperationResponse productUpdateValidator(ProductRequest productRequest, long id) {
        ProductOperationResponse response = new ProductOperationResponse();
        if (!productRequestValidator(productRequest)) {
            return response.setSuccess(false).setMessage(
                    "All fields required not to be empty and key length can be max 8!");
        }
        if (!repository.isProductKeyUnique(productRequest.getProductKey(), id)) {
            return response.setSuccess(false).setMessage("Product key must be unique!");
        }
        if (!repository.isNameUnique(productRequest.getName(), id)) {
            return response.setSuccess(false).setMessage("Name must be unique!");
        }
        if (!repository.isUrlUnique(productRequest.getUrl(), id)) {
            return response.setSuccess(false).setMessage("URL must be unique!");
        }
        return response.setSuccess(true);
    }

    public ProductOperationResponse productCreateValidator(ProductRequest productRequest) {
        ProductOperationResponse response = new ProductOperationResponse();
        if (!productRequestValidator(productRequest)) {
            return response.setSuccess(false).setMessage(
                    "All fields required not to be empty and key length can be max 8!");
        }
        if (!repository.isProductKeyUnique(productRequest.getProductKey())) {
            return response.setSuccess(false).setMessage(
                    "Product key must be unique!");
        }
        if (!repository.isNameUnique(productRequest.getName())) {
            return response.setSuccess(false).setMessage("Name must be unique!");
        }
        if (!repository.isUrlUnique(productRequest.getUrl())) {
            return response.setSuccess(false).setMessage("URL must be unique!");
        }
        return response.setSuccess(true);
    }

    private boolean productRequestValidator(ProductRequest productRequest) {
        if (productRequest.getProductKey() == null
                || productRequest.getProductKey() == ""
                || productRequest.getProductKey().length() > 8) {
            return false;
        }
        if (productRequest.getName() == null
                || productRequest.getName() == "") {
            return false;
        }
        if (productRequest.getUrl() == null
                || productRequest.getUrl() == "") {
            return false;
        }
        if (productRequest.getManufacturer() == null
                || productRequest.getManufacturer() == "") {
            return false;
        }
        if (productRequest.getPrice() <= 0) {
            return false;
        }
        return true;

    }
}
