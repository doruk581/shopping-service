package com.trendyol.shoppingservice.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ShoppingCartItemTest {

    @Test
    public void controlThatIncreaseQuantityMethodIncreaseAsArgument() {
        final Product product = Product.builder().id("1").price(BigDecimal.TEN).build();

        final ShoppingCartItem item = ShoppingCartItem.builder().product(product).quantity(5).build();

        item.increaseQuantity(5);

        assertThat(item.getQuantity(), is(equalTo(10)));
    }

    @Test
    public void controlThatTotalPriceMethodShouldReturnCorrectResult() {
        final Product product = Product.builder().id("1").price(BigDecimal.TEN).build();

        final ShoppingCartItem item = ShoppingCartItem.builder().product(product).quantity(5).build();

        BigDecimal totalPrice = item.totalPrice();

        assertThat(totalPrice, is(equalTo(BigDecimal.valueOf(50))));

        item.increaseQuantity(5);

        totalPrice = item.totalPrice();

        assertThat(totalPrice, is(equalTo(BigDecimal.valueOf(100))));

    }

}