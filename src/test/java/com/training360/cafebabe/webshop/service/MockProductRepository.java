package com.training360.cafebabe.webshop.service;

import com.training360.cafebabe.webshop.product.controller.ProductRequest;
import com.training360.cafebabe.webshop.product.entities.Product;
import com.training360.cafebabe.webshop.product.repository.PageableProductsResponse;
import com.training360.cafebabe.webshop.product.repository.ProductRepository;
import com.training360.cafebabe.webshop.product.repository.ProductResponse;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.logging.Logger;

public class MockProductRepository extends ProductRepository {
    private boolean deleteProductWasCalled;
    private long deleteProductParameter;
    private boolean deleteProductExpectedReturnValue;

    private boolean updateProductWasCalled;
    private long updateProductParameterId;
    private ProductRequest updateProductParameterProductRequest;
    private boolean updateProductExpectedReturnValue;

    private boolean createProductWasCalled;
    private ProductRequest createProductParameterProductRequest;
    private boolean createProductExpectedReturnValue;

    private boolean isNameUniqueExpectedReturnValue;
    private boolean isProductKeyUniqueExpectedReturnValue;
    private boolean isUrlUniqueExpectedReturnValue;

    private boolean listAllProductsWasCalled;
    private List<Product> listAllProductsExpectedReturnValue;

    private boolean getProductByUrlWasCalled;
    private String getProductByUrlParameterUrl;
    private ProductResponse getProductByUrlExpectedReturnValue;

    public MockProductRepository() {
        super(new DataSource() {
            @Override
            public Connection getConnection() {
                return null;
            }

            @Override
            public Connection getConnection(String username, String password) {
                return null;
            }

            @Override
            public PrintWriter getLogWriter() {
                return null;
            }

            @Override
            public void setLogWriter(PrintWriter out) {

            }

            @Override
            public int getLoginTimeout() {
                return 0;
            }

            @Override
            public void setLoginTimeout(int seconds) {

            }

            @Override
            public <T> T unwrap(Class<T> iface) {
                return null;
            }

            @Override
            public boolean isWrapperFor(Class<?> iface) {
                return false;
            }

            @Override
            public Logger getParentLogger() {
                return null;
            }
        });
    }


    public void setGetProductByUrlExpectedReturnValue(ProductResponse getProductByUrlExpectedReturnValue) {
        this.getProductByUrlExpectedReturnValue = getProductByUrlExpectedReturnValue;
    }


    public boolean isGetProductByUrlWasCalled() {
        return getProductByUrlWasCalled;
    }

    public String getGetProductByUrlParameterUrl() {
        return getProductByUrlParameterUrl;
    }

    @Override
    public ProductResponse getProductByUrl(String url) {
        getProductByUrlWasCalled = true;
        getProductByUrlParameterUrl = url;
        return getProductByUrlExpectedReturnValue;
    }

    public void setListAllProductsExpectedReturnValue(List<Product> listAllProductsExpectedReturnValue) {
        this.listAllProductsExpectedReturnValue = listAllProductsExpectedReturnValue;
    }

    public boolean isListAllProductsWasCalled() {
        return listAllProductsWasCalled;
    }

    @Override
    public List<Product> listAllProducts() {
        listAllProductsWasCalled = true;
        return listAllProductsExpectedReturnValue;
    }

    public boolean isDeleteProductWasCalled() {
        return deleteProductWasCalled;
    }

    public long getDeleteProductParameter() {
        return deleteProductParameter;
    }

    public void setDeleteProductExpectedReturnValue(boolean deleteProductExpectedReturnValue) {
        this.deleteProductExpectedReturnValue = deleteProductExpectedReturnValue;
    }

    @Override
    public boolean deleteProduct(long id) {
        deleteProductWasCalled = true;
        deleteProductParameter = id;
        return this.deleteProductExpectedReturnValue;
    }

    public boolean isUpdateProductWasCalled() {
        return updateProductWasCalled;
    }

    public long getUpdateProductParameterId() {
        return updateProductParameterId;
    }

    public ProductRequest getUpdateProductParameterProductRequest() {
        return updateProductParameterProductRequest;
    }

    public void setUpdateProductExpectedReturnValue(boolean updateProductExpectedReturnValue) {
        this.updateProductExpectedReturnValue = updateProductExpectedReturnValue;
    }

    @Override
    public boolean updateProduct(ProductRequest productRequest, long id) {
        updateProductWasCalled = true;
        updateProductParameterId = id;
        updateProductParameterProductRequest = productRequest;
        return this.updateProductExpectedReturnValue;
    }

    public boolean isCreateProductWasCalled() {
        return createProductWasCalled;
    }

    public ProductRequest getCreateProductParameterProductRequest() {
        return createProductParameterProductRequest;
    }

    public void setCreateProductExpectedReturnValue(boolean createProductExpectedReturnValue) {
        this.createProductExpectedReturnValue = createProductExpectedReturnValue;
    }

    @Override
    public boolean createProduct(ProductRequest productRequest) {
        createProductWasCalled = true;
        createProductParameterProductRequest = productRequest;
        return this.createProductExpectedReturnValue;
    }

    public void setNameUniqueExpectedReturnValue(boolean nameUniqueExpectedReturnValue) {
        isNameUniqueExpectedReturnValue = nameUniqueExpectedReturnValue;
    }

    public void setProductKeyUniqueExpectedReturnValue(boolean productKeyUniqueExpectedReturnValue) {
        isProductKeyUniqueExpectedReturnValue = productKeyUniqueExpectedReturnValue;
    }

    public void setUrlUniqueExpectedReturnValue(boolean urlUniqueExpectedReturnValue) {
        isUrlUniqueExpectedReturnValue = urlUniqueExpectedReturnValue;
    }

    @Override
    public boolean isNameUnique(String value) {
        return isNameUniqueExpectedReturnValue ;
    }

    @Override
    public boolean isProductKeyUnique(String value) {
        return isProductKeyUniqueExpectedReturnValue;
    }

    @Override
    public boolean isUrlUnique(String value) {
        return isUrlUniqueExpectedReturnValue;
    }

    @Override
    public PageableProductsResponse listProducts(int start, int size) {
        return super.listProducts(start, size);
    }

    @Override
    public int numberOfProducts() {
        return super.numberOfProducts();
    }

    @Override
    public Product getProductById(long id) {
        return super.getProductById(id);
    }

    @Override
    public boolean isNameUnique(String value, long id) {
        return isNameUniqueExpectedReturnValue;
    }

    @Override
    public boolean isProductKeyUnique(String value, long id) {
        return isProductKeyUniqueExpectedReturnValue;
    }

    @Override
    public boolean isUrlUnique(String value, long id) {
        return isUrlUniqueExpectedReturnValue;
    }
}
