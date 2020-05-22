package com.trendyol.shoppingservice.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItem {

    private Product product;
    private Integer quantity;

    public void increaseQuantity(final Integer quantity) {
        this.quantity += quantity;
    }

    public BigDecimal totalPrice() {
        return this.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
