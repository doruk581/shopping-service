package com.trendyol.shoppingservice.infrastructure.product;

import com.trendyol.shoppingservice.domain.Product;

import java.util.Optional;

/*
 * Consider Product is another domain service...
 * */
public interface ProductService {

    Optional<Product> getProductById(final String id);
}
