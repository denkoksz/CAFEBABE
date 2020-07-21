package com.training360.cafebabe.webshop.order.entities;

import com.training360.cafebabe.webshop.product.entities.Product;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OrderTest {

    @Test
    public void TestConstructor() {
        Order order = new Order(1L, "testuser", LocalDateTime.now(), Collections.emptyList(), 15400, OrderStatus.ACTIVE);

        assertThat(order.getId(), equalTo(1L));
        assertThat(order.getUserName(), equalTo("testuser"));
        assertThat(order.getProducts().isEmpty(), equalTo(true));
        assertThat(order.getSum(), equalTo(15400));
        assertThat(order.getStatus(), equalTo(OrderStatus.ACTIVE));
    }
}
