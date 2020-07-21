package com.training360.cafebabe.webshop.order.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/cleanDBforOrdersTesting.sql")
public class OrderRepositoryIntegrationTest {
    @Autowired
    private OrderRepository repo;

    @Test
    public void getNumberOfOrdersShouldReturnANumberOfAllOrders(){
        //When
        int result = repo.getNumberOfOrders();
        //Then
        assertEquals(12, result);
    }

    @Test
    public void getNumberOfActiveOrdersShouldReturnANumberOfActiveOrders(){
        //When
        int result = repo.getNumberOfActiveOrders();
        //Then
        assertEquals(8, result);
    }


}
