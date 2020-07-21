package com.training360.cafebabe.webshop.product.service;

import com.training360.cafebabe.webshop.product.controller.ProductRequest;
import com.training360.cafebabe.webshop.product.entities.Product;
import com.training360.cafebabe.webshop.product.repository.PageableProductsResponse;
import com.training360.cafebabe.webshop.product.repository.ProductRepository;
import com.training360.cafebabe.webshop.product.repository.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository repository;
    private ProductValidator validator;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
        validator = new ProductValidator(repository);
    }

    public ProductResponse getProductByUrl(String url) {
        return repository.getProductByUrl(url);
    }

    public List<Product> listAllProducts() {
        return repository.listAllProducts();
    }

    public ProductOperationResponse deleteProduct(long id) {
        ProductOperationResponse response = new ProductOperationResponse();
        if (repository.deleteProduct(id)) {
            return response.setSuccess(true).setMessage("Product successfully deleted");
        } else {
            return response.setSuccess(false).setMessage("Product could not be deleted!");
        }
    }

    public ProductOperationResponse updateProduct(ProductRequest productRequest, long id) {
        ProductOperationResponse response = validator.productUpdateValidator(productRequest, id);
        if (response.isSuccess()) {
            if (repository.updateProduct(productRequest, id)) {
                return response.setMessage("Product successfully updated!");
            } else {
                return response.setMessage("Product could not be updated : SQL Database refused");
            }
        } else {
            return response;
        }
    }

    public ProductOperationResponse createProduct(ProductRequest productRequest) {
        ProductOperationResponse response = validator.productCreateValidator(productRequest);
        if (response.isSuccess()) {
            if (repository.createProduct(productRequest)) {
                return response.setMessage("Product successfully created!");
            } else {
                return response.setMessage("Product could not be created : SQL Database refused");
            }
        } else {
            return response;
        }
    }

    public PageableProductsResponse listProduct(int start, int size) {
        PageableProductsResponse response = repository.listProducts(start, size);

        int numberOfProducts = repository.numberOfActiveProducts();

        if (numberOfProducts % size != 0) {
            response.setTotalNumberOfPages((numberOfProducts / size) + 1);
        } else {
            response.setTotalNumberOfPages(numberOfProducts / size);
        }

        response.setCurrentPage(start / size + 1);

        return response;
    }

    public int getNumberOfProducts(){
        return repository.numberOfProducts();
    }

    public int getNumberOfActiveProducts(){
        return repository.numberOfActiveProducts();
    }


}
