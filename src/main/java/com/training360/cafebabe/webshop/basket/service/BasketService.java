package com.training360.cafebabe.webshop.basket.service;

import com.training360.cafebabe.webshop.basket.entities.Basket;
import com.training360.cafebabe.webshop.basket.repository.BasketRepository;
import com.training360.cafebabe.webshop.product.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {

    private BasketRepository repository;


    public BasketService(BasketRepository repository) {
        this.repository = repository;
    }

    public BasketOperationResponse addProduct(Long id, String username) {
        BasketOperationResponse response = new BasketOperationResponse();
        if (!repository.isProductAlreadyInTheBasket(id, username)) {
            repository.addProduct(id, username);
            return response.setSuccess(true).setMessage("Product added to basket!");
        } else return response.setSuccess(false).setMessage("Product is already in the basket!");
    }

    public Basket getBasket(String username) {
        List<Product> products = repository.getBasket(username);
        System.out.println(products);
        int sum = repository.sumBasketPrice(username);
        System.out.println(sum);
        return new Basket(sum, products);
    }

    public BasketOperationResponse deleteBasket(String username) {
        BasketOperationResponse response = new BasketOperationResponse();
        if (repository.deleteBasket(username) == true) {
            return response.setSuccess(true).setMessage("Basket is deleted!");
        } else return response.setSuccess(false).setMessage("Basket is already empty!");
    }

    public BasketOperationResponse deleteProduct(Long id, String username) {
        BasketOperationResponse response = new BasketOperationResponse();
        if (repository.deleteProduct(id, username) == true){
            return response.setSuccess(true).setMessage("Product is deleted from basket!");
        } else return response.setSuccess(false).setMessage("Product does not contains product!");
    }
}
