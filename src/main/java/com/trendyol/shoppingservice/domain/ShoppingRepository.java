package com.trendyol.shoppingservice.domain;

import java.util.Optional;

public interface ShoppingRepository {

    Optional<ShoppingCart> findByCartId(final String chartId);

    void save(final ShoppingCart shoppingCart);
}
