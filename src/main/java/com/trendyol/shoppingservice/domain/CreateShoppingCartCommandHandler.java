package com.trendyol.shoppingservice.domain;

import com.trendyol.shoppingservice.domain.commands.CommandHandler;
import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;
import com.trendyol.shoppingservice.domain.flow.*;
import com.trendyol.shoppingservice.infrastructure.product.ProductService;

import java.util.Optional;

public class CreateShoppingCartCommandHandler implements CommandHandler<CreateItemCommand> {

    private final ProductService productService;

    private final ShoppingRepository shoppingRepository;

    private final InitializeCampaignDiscountHandler initializeCampaignDiscountHandler;

    private final InitializeCouponDiscountHandler initializeCouponDiscountHandler;

    private final InitializeDeliveryHandler initializeDeliveryHandler;

    private final InitializeRepositoryHandler initializeRepositoryHandler;

    private final InitializeCartLoggerHandler initializeCartLoggerHandler;

    public CreateShoppingCartCommandHandler(ProductService productService,
                                            ShoppingRepository shoppingRepository,
                                            InitializeCampaignDiscountHandler initializeCampaignDiscountHandler,
                                            InitializeCouponDiscountHandler initializeCouponDiscountHandler,
                                            InitializeDeliveryHandler initializeDeliveryHandler,
                                            InitializeRepositoryHandler initializeRepositoryHandler,
                                            InitializeCartLoggerHandler initializeCartLoggerHandler) {
        this.productService = productService;
        this.shoppingRepository = shoppingRepository;
        this.initializeCampaignDiscountHandler = initializeCampaignDiscountHandler;
        this.initializeCouponDiscountHandler = initializeCouponDiscountHandler;
        this.initializeCartLoggerHandler = initializeCartLoggerHandler;
        this.initializeDeliveryHandler = initializeDeliveryHandler;
        this.initializeRepositoryHandler = initializeRepositoryHandler;
    }

    @Override
    public void handle(final CreateItemCommand createItemCommand) {
        final Optional<Product> product = productService.getProductById(createItemCommand.getId());
        if (product.isEmpty())
            throw ProductNotExistException.create(createItemCommand.getId());

        initializeCampaignDiscountHandler.setSuccessor(initializeCouponDiscountHandler);
        initializeCouponDiscountHandler.setSuccessor(initializeDeliveryHandler);
        initializeDeliveryHandler.setSuccessor(initializeCartLoggerHandler);
        initializeCartLoggerHandler.setSuccessor(initializeRepositoryHandler);

        final Optional<ShoppingCart> maybeShoppingCart = shoppingRepository.findByCartId(createItemCommand.getCommandId());

        //TODO:REFACTOR!!
        if (maybeShoppingCart.isEmpty()) {
            final ShoppingCart shoppingCart = ShoppingCart.create(createItemCommand.getCommandId());
            shoppingCart.addItem(product.get(), createItemCommand.getQuantity());
            initializeCampaignDiscountHandler.handle(new ShoppingContext(shoppingCart, createItemCommand));

        } else {
            final ShoppingCart shoppingCart = maybeShoppingCart.get();
            shoppingCart.addItem(product.get(), createItemCommand.getQuantity());
            initializeCampaignDiscountHandler.handle(new ShoppingContext(shoppingCart, createItemCommand));
        }
    }
}
