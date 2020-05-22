package com.trendyol.shoppingservice.domain;

import com.trendyol.shoppingservice.domain.commands.CommandHandler;
import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;

public class DefaultShoppingService implements ShoppingService {

    private final CommandHandler<CreateItemCommand> createItemCommandCommandHandler;

    public DefaultShoppingService(CommandHandler<CreateItemCommand> createItemCommandCommandHandler) {
        this.createItemCommandCommandHandler = createItemCommandCommandHandler;
    }

    @Override
    public void create(CreateItemCommand command) {
        createItemCommandCommandHandler.handle(command);
    }
}
