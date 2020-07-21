package com.training360.cafebabe.webshop.product.entities;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ProductTest {
    @Test
    public void TestConstructor() {
        Product product = new Product(1, "abc", "tükör", "tukor", "valami", 500, true);

        assertThat(product.getId(), equalTo(1L));
        assertThat(product.getProductKey(), equalTo("abc"));
        assertThat(product.getName(), equalTo("tükör"));
        assertThat(product.getUrl(), equalTo("tukor"));
        assertThat(product.getManufacturer(), equalTo("valami"));
        assertThat(product.getPrice(), equalTo(500));
        assertThat(product.isActive(), equalTo(true));
    }
}
