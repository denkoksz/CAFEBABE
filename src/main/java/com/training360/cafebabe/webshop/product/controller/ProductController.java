package com.training360.cafebabe.webshop.product.controller;

import com.training360.cafebabe.webshop.product.entities.Product;
import com.training360.cafebabe.webshop.product.repository.PageableProductsResponse;
import com.training360.cafebabe.webshop.product.repository.ProductResponse;
import com.training360.cafebabe.webshop.product.service.ProductOperationResponse;
import com.training360.cafebabe.webshop.product.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
public class ProductController {
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/products", params = "url")
    public ProductResponse getProductByUrl(@RequestParam("url") String url) {
        return service.getProductByUrl(url);
    }

    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    public List<Product> listAllProducts(Authentication authentication) {
        if (authentication != null && isAdmin(authentication)) {
            return service.listAllProducts();
        }
        return Collections.emptyList();
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.DELETE)
    public ProductOperationResponse deleteProduct(@PathVariable long id, Authentication authentication) {
        if (authentication != null && isAdmin(authentication)) {
            return service.deleteProduct(id);
        }
        return new ProductOperationResponse().setSuccess(false).setMessage("You dont have previligies to delete product");
    }

    @RequestMapping(value = "/api/products", method = RequestMethod.POST)
    public ProductOperationResponse createProduct(@RequestBody ProductRequest productRequest, Authentication authentication) {
        if (authentication != null && isAdmin(authentication)) {
            return service.createProduct(productRequest);
        }
        return new ProductOperationResponse().setSuccess(false).setMessage("You dont have previligies to create product");

    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.POST)
    public ProductOperationResponse updateProduct(@RequestBody ProductRequest productRequest, @PathVariable long id, Authentication authentication) {
        if (authentication != null && isAdmin(authentication)) {
            return service.updateProduct(productRequest, id);
        }
        return new ProductOperationResponse().setSuccess(false).setMessage("You dont have previligies to update product");

    }

    //bejövő paraméterek alapján ad vissza egy adott hosszú product-okból álló listát
    @RequestMapping(value = "/api/products", params = {"start", "size"})
    public PageableProductsResponse listProducts(@RequestParam("start") Integer start,
                                                 @RequestParam("size") Integer size, Authentication authentication) {
        if (size < 1) {
            throw new IllegalArgumentException("Size must be positive!");
        }
        if (start < 0) {
            throw new IllegalArgumentException("Start must be 0 or more than 0");
        }
        return service.listProduct(start, size);
    }

    //megnézi, hogy a bejelentkezett felhasználó admin jogosultságú-e
    private boolean isAdmin(Authentication authentication) {
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

}
