package com.training360.cafebabe.webshop.basket.controller;

import com.training360.cafebabe.webshop.basket.entities.Basket;
import com.training360.cafebabe.webshop.basket.service.BasketOperationResponse;
import com.training360.cafebabe.webshop.basket.service.BasketService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasketController {

    private BasketService service;

    public BasketController(BasketService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/basket/{id}", method = RequestMethod.POST)
    public BasketOperationResponse addProduct(@PathVariable Long id, Authentication authentication) {
        if (authentication != null) {

            UserDetails details = (UserDetails) authentication.getPrincipal();

            String username = details.getUsername();
            return service.addProduct(id, username);
        }
        throw new IllegalArgumentException("You must be logged in to add product to basket");
    }

    @RequestMapping(value = "/api/basket", method = RequestMethod.GET)
    public Basket getBasket(Authentication authentication) {
        if (authentication != null) {
            UserDetails details = (UserDetails) authentication.getPrincipal();
            String username = details.getUsername();
            System.out.println(username);
        return service.getBasket(username);
        }
        throw new IllegalArgumentException("You must be logged in to get you're basket");
    }

    @RequestMapping(value = "/api/basket", method = RequestMethod.DELETE)
    public BasketOperationResponse deleteBasket(Authentication authentication){
        if (authentication != null) {
            UserDetails details = (UserDetails) authentication.getPrincipal();
            String username = details.getUsername();
        return service.deleteBasket(username);
        }
        throw new IllegalArgumentException("You must be logged in to delete you're basket");
    }

    @RequestMapping(value = "/api/basket/{id}", method = RequestMethod.DELETE)
    public BasketOperationResponse deleteProduct(@PathVariable Long id, Authentication authentication) {
        if (authentication != null) {
            UserDetails details = (UserDetails) authentication.getPrincipal();
            String username = details.getUsername();
            return service.deleteProduct(id,username );
        }
        throw new IllegalArgumentException("You must be logged in to delete product from basket");
    }


}
