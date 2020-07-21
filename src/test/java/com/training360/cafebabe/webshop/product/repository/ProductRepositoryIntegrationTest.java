package com.training360.cafebabe.webshop.repository;


import com.training360.cafebabe.webshop.product.controller.ProductRequest;
import com.training360.cafebabe.webshop.product.entities.Product;
import com.training360.cafebabe.webshop.product.repository.ProductRepository;
import com.training360.cafebabe.webshop.product.repository.ProductResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/clean.sql")
public class ProductRepositoryIntegrationTest {
    @Autowired
    private ProductRepository repo;

    @Test
    public void testGetProductByUrl() {
        ProductResponse response = repo.getProductByUrl("corfu_coffee");

        assertEquals(true, response.getPresent());
        Product product = response.getProduct();

        assertEquals("Tchibo", product.getManufacturer());
        assertEquals(6900, product.getPrice());
        assertEquals("Corfu Coffee", product.getName());
    }

    @Test
    @Sql("classpath:/addmultipleproducts.sql")
    public void getProductByIdTest() {
        List<Product> allActiveProducts = repo.listAllProducts();
        long id = allActiveProducts.get(0).getId();
        String url = allActiveProducts.get(0).getUrl();
        String name = allActiveProducts.get(0).getName();
        Product product = repo.getProductById(id);

        assertEquals(url, product.getUrl());
        assertEquals(name, product.getName() );
    }

    @Test
    @Sql("classpath:/addmultipleproducts.sql")
    public void listAllProductsShouldReturnAllActiveProducts() {
        List<Product> allActiveProducts = repo.listAllProducts();

        assertNotNull(allActiveProducts);
        assertEquals(2, allActiveProducts.size());
        for (Product product : allActiveProducts) {
            assertEquals(true, product.isActive());
            assertNotEquals("NotActiveCoffee",product.getName());
        }
    }

    @Test
    @Sql("classpath:/addmultipleproducts.sql")
    public void deleteProductShouldDeleteSelectedItem () {
        List<Product> allActiveProducts = repo.listAllProducts();
        long id = allActiveProducts.get(0).getId();
        String url = allActiveProducts.get(0).getUrl();
        boolean isProductDeleted = repo.deleteProduct(id);
        allActiveProducts = repo.listAllProducts();
        Product product = repo.getProductById(id);

        assertEquals(1, allActiveProducts.size());
        assertEquals(true, isProductDeleted);
        assertEquals(false, product.isActive());
    }


    @Test
    @Sql("classpath:/addmultipleproducts.sql")
    public void updateProductShouldModifySelectedItem () {
        List<Product> allActiveProducts = repo.listAllProducts();
        long id = allActiveProducts.get(0).getId();

        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("11223344");
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        boolean isProductUpdated = repo.updateProduct(productRequest,id);
        allActiveProducts = repo.listAllProducts();
        Product product = repo.getProductById(id);

        assertEquals(true, isProductUpdated);
        assertEquals(2, allActiveProducts.size());
        assertEquals(true, product.isActive());
    }

    @Test
    @Sql("classpath:/addmultipleproducts.sql")
    public void createProductShouldAddNewProductItemToRepository () {
        String url = "test_caffe";

        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("11223344");
        productRequest.setName("TestCaffe");
        productRequest.setUrl(url);
        productRequest.setManufacturer("11223344");
        productRequest.setPrice(4000);

        boolean isProductCreated = repo.createProduct(productRequest);
        List<Product> allActiveProducts = repo.listAllProducts();
        ProductResponse productResponse = repo.getProductByUrl(url);

        assertEquals(true, isProductCreated);
        assertEquals(3, allActiveProducts.size());
        assertEquals(true, productResponse.getPresent());
        assertEquals("TestCaffe", productResponse.getProduct().getName());
        assertEquals("11223344", productResponse.getProduct().getProductKey());
        assertEquals(url, productResponse.getProduct().getUrl());
        assertEquals("11223344", productResponse.getProduct().getManufacturer());
        assertEquals(4000, productResponse.getProduct().getPrice());
        assertEquals(true, productResponse.getProduct().isActive());
    }

    @Test
    @Sql("classpath:/addmultipleproducts.sql")
    public void isNameUniqueShouldReturnTrueIfNoProductHasTheSameName () {
        //When
        boolean result = repo.isNameUnique("DummyName");

        //Then
        assertEquals(true, result);
    }

    @Test
    @Sql("classpath:/addmultipleproducts.sql")
    public void isNameUniqueShouldReturnFalseIfAProductHasTheSameName () {
        //When
        boolean result = repo.isNameUnique("Australian Coffee ");

        //Then
        assertEquals(false, result);
    }

    @Test
    @Sql("classpath:/addmultipleproducts.sql")
    public void isProductKeyUniqueShouldReturnTrueIfNoProductHasTheSameProductKey () {
        //When
        boolean result = repo.isProductKeyUnique("DummyProductKey");

        //Then
        assertEquals(true, result);
    }

    @Test
    @Sql("classpath:/addmultipleproducts.sql")
    public void isProductKeyUniqueShouldReturnFalseIfAProductHasTheSameProductKey () {
        //When
        boolean result = repo.isProductKeyUnique("MMS83mf0");

        //Then
        assertEquals(false, result);
    }

    @Test
    @Sql("classpath:/addmultipleproducts.sql")
    public void isUrlUniqueShouldReturnTrueIfNoProductHasTheSameUrl () {
        //When
        boolean result = repo.isUrlUnique("DummyUrl");

        //Then
        assertEquals(true, result);
    }

    @Test
    @Sql("classpath:/addmultipleproducts.sql")
    public void isUrlUniqueShouldReturnFalseIfAProductHasTheSameUrl () {
        //When
        boolean result = repo.isUrlUnique("australian_coffee");

        //Then
        assertEquals(false, result);
    }

}
