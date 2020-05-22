package com.trendyol.shoppingservice.domain.flow;

import com.trendyol.shoppingservice.domain.ShoppingContext;
import com.trendyol.shoppingservice.domain.ShoppingRepository;

public class InitializeRepositoryHandler implements Handler<ShoppingContext> {

    private Handler<ShoppingContext> successor;

    private ShoppingRepository shoppingRepository;

    public InitializeRepositoryHandler(ShoppingRepository shoppingRepository) {
        this.shoppingRepository = shoppingRepository;
    }

    @Override
    public void setSuccessor(Handler<ShoppingContext> handler) {
        this.successor = handler;
    }

    @Override
    public void handle(ShoppingContext context) {

        shoppingRepository.save(context.getShoppingCart());

        if (successor != null) successor.handle(context);
    }
}
