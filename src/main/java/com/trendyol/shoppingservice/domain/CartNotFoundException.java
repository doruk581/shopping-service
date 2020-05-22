package com.trendyol.shoppingservice.domain;

public class CartNotFoundException extends RuntimeException {

    private String id;

    private CartNotFoundException(String id) {
        this.id = id;
    }

    public static CartNotFoundException create(String id) {
        return new CartNotFoundException(id);
    }

    public String getMessage() {
        return "Cart Information not found for id: " + id;
    }
}
