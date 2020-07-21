package com.training360.cafebabe.webshop.basket.entities;

import com.training360.cafebabe.webshop.product.entities.Product;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BasketTest {

    @Test
    public void testConstructor(){

        List<Product> products = new ArrayList<>();
        products.add(new Product(123,"qwertzui" , "testcaffe","testcaffe" ,"tester" , 12345,true ));
        products.add(new Product(124,"mnbvcxyl" , "testcaffe2","testcaffe2" ,"tester" , 12346,true ));
        Basket basket = new Basket(14500,products);

        assertThat(basket.getSum(),equalTo(14500) );
        assertThat(basket.getProducts().get(0).getName(),equalTo("testcaffe") );
        assertThat(basket.getProducts().get(1).getProductKey(),equalTo("mnbvcxyl") );
        assertThat(basket.getProducts().size(),equalTo(2) );

    }
}
