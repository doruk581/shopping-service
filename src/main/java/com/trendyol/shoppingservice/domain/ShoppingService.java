package com.trendyol.shoppingservice.domain;

import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;

public interface ShoppingService {

    void create(final CreateItemCommand command);
}
