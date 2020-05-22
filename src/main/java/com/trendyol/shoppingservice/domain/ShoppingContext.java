package com.trendyol.shoppingservice.domain;

import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;
import lombok.Getter;

@Getter
public class ShoppingContext {

    private ShoppingCart shoppingCart;
    private CreateItemCommand createItemCommand;

    public ShoppingContext(ShoppingCart shoppingCart, CreateItemCommand createItemCommand) {
        this.shoppingCart = shoppingCart;
        this.createItemCommand = createItemCommand;
    }
}
