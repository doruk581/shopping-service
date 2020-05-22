package com.trendyol.shoppingservice.domain;

import com.trendyol.shoppingservice.domain.commands.CommandHandler;
import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;

public class DefaultShoppingService implements ShoppingService {

    private final CommandHandler<CreateItemCommand> createItemCommandCommandHandler;

    private final ShoppingRepository shoppingRepository;

    public DefaultShoppingService(CommandHandler<CreateItemCommand> createItemCommandCommandHandler, ShoppingRepository shoppingRepository) {
        this.createItemCommandCommandHandler = createItemCommandCommandHandler;
        this.shoppingRepository = shoppingRepository;
    }

    @Override
    public void create(CreateItemCommand command) {
        createItemCommandCommandHandler.handle(command);
    }

    @Override
    public ShoppingCart getShoppingCart(String id) {
        return shoppingRepository.findByCartId(id).orElseThrow(() -> CartNotFoundException.create(id));
    }
}
