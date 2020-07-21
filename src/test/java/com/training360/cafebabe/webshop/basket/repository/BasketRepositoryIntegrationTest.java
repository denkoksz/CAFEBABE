package com.training360.cafebabe.webshop.basket.repository;

import com.training360.cafebabe.webshop.basket.service.BasketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/addbasketitems.sql")
public class BasketRepositoryIntegrationTest {

    @Autowired
    private BasketRepository repo;
    @Autowired
    private BasketService service;


    @Test
    public void addProductsToBasket() {
        repo.addProduct(59L,"Antal");
        repo.addProduct(58L,"Antal");
        assertEquals(true, repo.isProductAlreadyInTheBasket(59L,"Antal" ));
        assertEquals(7200, repo.sumBasketPrice("Antal"));
    }

    @Test
    public void addProductTwice(){
        service.addProduct(58L,"Antal");
        assertEquals(1, repo.getBasket("Antal").size());
        service.addProduct(58L,"Antal");
        service.addProduct(58L,"Antal");
        service.addProduct(59L,"Antal");
        assertEquals(2, repo.getBasket("Antal").size());
    }

    @Test
    public void deleteBasket(){
        service.addProduct(37L,"Antal");
        service.addProduct(36L,"Antal");
        assertEquals(true, service.deleteBasket("Antal").isSuccess() );
        assertEquals(false, service.deleteBasket("Antal").isSuccess() );
    }

    @Test
    public void deleteProductByIdAndUsername(){
        repo.addProduct(58L,"Antal");
        repo.addProduct(59L,"Antal");
        assertEquals(2,repo.getBasket("Antal").size() );
        assertEquals(true, repo.deleteProduct(58L, "Antal"));
        assertEquals(1, repo.getBasket("Antal").size() );
    }



}
