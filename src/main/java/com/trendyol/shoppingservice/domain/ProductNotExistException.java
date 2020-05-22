package com.trendyol.shoppingservice.domain;

public class ProductNotExistException extends RuntimeException {

    private String id;

    private ProductNotExistException(String id) {
        this.id = id;
    }

    public static ProductNotExistException create(String id) {
        return new ProductNotExistException(id);
    }

    public String getMessage() {
        return "Product Information not found for id: " + id;
    }

}
