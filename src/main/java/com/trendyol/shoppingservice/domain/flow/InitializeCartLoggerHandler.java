package com.trendyol.shoppingservice.domain.flow;

import com.trendyol.shoppingservice.domain.ShoppingCart;
import com.trendyol.shoppingservice.domain.ShoppingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitializeCartLoggerHandler implements Handler<ShoppingContext> {

    private Handler<ShoppingContext> successor;

    @Override
    public void setSuccessor(Handler<ShoppingContext> handler) {
        this.successor = handler;
    }

    @Override
    public void handle(ShoppingContext context) {

        final ShoppingCart shoppingCart = context.getShoppingCart();

        log.warn("Shopping cart with id: " + shoppingCart.getCartId() + " contains " + shoppingCart.getItemList().size()
                + " products");

        shoppingCart.getItemList()
                .forEach(item -> {
                    log.warn("Product : " + item.getProduct().getTitle() + " Quantity: " + item.getQuantity() + "Category: " + item.getProduct().getCategory().getId()
                            + " Unit price: " + item.getProduct().getPrice() + " Total Price: " + item.totalPrice() + " Total Discount Applied: " + shoppingCart.totalDiscount());
                });

        if (this.successor != null) successor.handle(context);
    }
}
