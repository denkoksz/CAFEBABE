package com.training360.cafebabe.webshop.product.repository;

import com.training360.cafebabe.webshop.product.entities.Product;

import java.util.List;

/* javascript lapozáshoz szükséges osztály
*   tartalmazza:
*   - az összes oldalszámot
*   - az aktuális oldalszámot
*   - az aktulális oldalon szereplő termékeket egy listában
 */
public class PageableProductsResponse {
    private int totalNumberOfPages;
    private int currentPage;
    private List<Product> page;

    public PageableProductsResponse(List<Product> page) {
        this.page = page;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalNumberOfPages() {
        return totalNumberOfPages;
    }

    public void setTotalNumberOfPages(int totalNumberOfPages) {
        this.totalNumberOfPages = totalNumberOfPages;
    }

    public List<Product> getPage() {
        return page;
    }
}
