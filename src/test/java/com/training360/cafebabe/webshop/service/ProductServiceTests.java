package com.training360.cafebabe.webshop.service;

import com.training360.cafebabe.webshop.product.controller.ProductRequest;
import com.training360.cafebabe.webshop.product.service.ProductOperationResponse;
import com.training360.cafebabe.webshop.product.service.ProductService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductServiceTests {
    @Test
    public void deleteProductShouldCallDeleteProductOnRepository() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setDeleteProductExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.deleteProduct(34);

        // Then
        assertEquals(true, mockedRepo.isDeleteProductWasCalled());
        assertEquals(34, mockedRepo.getDeleteProductParameter());
        assertEquals(true, result.isSuccess());
        assertEquals("Product successfully deleted",result.getMessage());
    }

    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfProductRequestIsValid() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("11223344");
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(true, mockedRepo.isUpdateProductWasCalled());
        assertEquals(76, mockedRepo.getUpdateProductParameterId());
        assertEquals(true, result.isSuccess());
        assertEquals("Product successfully updated!",result.getMessage());
    }


    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfProductKeyInProductRequestIsNull() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(false, mockedRepo.isUpdateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfProductKeyInProductRequestIsMoreThan8Chars() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("123456789");
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(false, mockedRepo.isUpdateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfProductKeyInProductRequestIsNotUnique() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("12345678");
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setProductKeyUniqueExpectedReturnValue(false);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(false, mockedRepo.isUpdateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("Product key must be unique!",result.getMessage());
    }


    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfNameInProductRequestIsNull() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("12345678");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(false, mockedRepo.isUpdateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfNameInProductRequestIsEmpty() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("123456789");
        productRequest.setName("");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(false, mockedRepo.isUpdateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfNameInProductRequestIsNotUnique() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("12345678");
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(false);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(false, mockedRepo.isUpdateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("Name must be unique!",result.getMessage());
    }

    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfManufacturerInProductRequestIsNull() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("12345678");
        productRequest.setName("test_caffe");
        productRequest.setUrl("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(false, mockedRepo.isUpdateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfManufacturerInProductRequestIsEmpty() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("123456789");
        productRequest.setName("test_caffe");
        productRequest.setManufacturer("");
        productRequest.setUrl("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(false, mockedRepo.isUpdateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }


    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfPriceInProductRequestIsLessThanZero() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("123456789");
        productRequest.setName("test_caffe");
        productRequest.setManufacturer("Tchibo");
        productRequest.setUrl("CaffeCo");
        productRequest.setPrice(-4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(false, mockedRepo.isUpdateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfUrlInProductRequestIsNotUnique() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("12345678");
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(false);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(false, mockedRepo.isUpdateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("URL must be unique!",result.getMessage());
    }


    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfUrlInProductRequestIsNull() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("12345678");
        productRequest.setName("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(false, mockedRepo.isUpdateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void updateProductShouldCallUpdateProductOnRepositoryIfUrlInProductRequestIsEmpty() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setUpdateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("123456789");
        productRequest.setName("test_caffe");
        productRequest.setUrl("");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.updateProduct(productRequest, 76);

        // Then
        assertEquals(false, mockedRepo.isUpdateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfProductRequestIsValid() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("11223344");
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(true, mockedRepo.isCreateProductWasCalled());
        assertEquals(true, result.isSuccess());
        assertEquals("Product successfully created!",result.getMessage());
    }

    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfProductKeyInProductRequestIsNull() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(false, mockedRepo.isCreateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfProductKeyInProductRequestIsMoreThan8Chars() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("123456789");
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(false, mockedRepo.isCreateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfProductKeyInProductRequestIsNotUnique() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("12345678");
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setProductKeyUniqueExpectedReturnValue(false);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(false, mockedRepo.isCreateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("Product key must be unique!",result.getMessage());
    }


    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfNameInProductRequestIsNull() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("12345678");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(false, mockedRepo.isCreateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfNameInProductRequestIsEmpty() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("123456789");
        productRequest.setName("");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(false, mockedRepo.isCreateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfNameInProductRequestIsNotUnique() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("12345678");
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(false);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(false, mockedRepo.isCreateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("Name must be unique!",result.getMessage());
    }

    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfManufacturerInProductRequestIsNull() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("12345678");
        productRequest.setName("test_caffe");
        productRequest.setUrl("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(false, mockedRepo.isCreateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfManufacturerInProductRequestIsEmpty() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("123456789");
        productRequest.setName("test_caffe");
        productRequest.setManufacturer("");
        productRequest.setUrl("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(false, mockedRepo.isCreateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }


    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfPriceInProductRequestIsLessThanZero() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("123456789");
        productRequest.setName("test_caffe");
        productRequest.setManufacturer("Tchibo");
        productRequest.setUrl("CaffeCo");
        productRequest.setPrice(-4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(false, mockedRepo.isCreateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfUrlInProductRequestIsNotUnique() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("12345678");
        productRequest.setName("TestCaffe");
        productRequest.setUrl("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(false);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(false, mockedRepo.isCreateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("URL must be unique!",result.getMessage());
    }


    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfUrlInProductRequestIsNull() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("12345678");
        productRequest.setName("test_caffe");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(false, mockedRepo.isCreateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }

    @Test
    public void createProductShouldCallCreateProductOnRepositoryIfUrlInProductRequestIsEmpty() {
        // Given
        MockProductRepository mockedRepo = new MockProductRepository();
        mockedRepo.setCreateProductExpectedReturnValue(true);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductKey("123456789");
        productRequest.setName("test_caffe");
        productRequest.setUrl("");
        productRequest.setManufacturer("CaffeCo");
        productRequest.setPrice(4000);

        mockedRepo.setProductKeyUniqueExpectedReturnValue(true);
        mockedRepo.setNameUniqueExpectedReturnValue(true);
        mockedRepo.setUrlUniqueExpectedReturnValue(true);

        // When
        ProductService productService = new ProductService(mockedRepo);
        ProductOperationResponse result = productService.createProduct(productRequest);

        // Then
        assertEquals(false, mockedRepo.isCreateProductWasCalled());
        assertEquals(false, result.isSuccess());
        assertEquals("All fields required not to be empty and key length can be max 8!",result.getMessage());
    }
}
