package com.trendyol.shoppingservice;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.trendyol.shoppingservice.application.validation.CreateItemValidationService;
import com.trendyol.shoppingservice.application.validation.ValidationService;
import com.trendyol.shoppingservice.domain.*;
import com.trendyol.shoppingservice.domain.commands.CommandHandler;
import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;
import com.trendyol.shoppingservice.domain.flow.*;
import com.trendyol.shoppingservice.infrastructure.DatabaseConfiguration;
import com.trendyol.shoppingservice.infrastructure.campaign.CampaignService;
import com.trendyol.shoppingservice.infrastructure.campaign.CampaignServiceGateway;
import com.trendyol.shoppingservice.infrastructure.product.ProductService;
import com.trendyol.shoppingservice.infrastructure.product.ProductServiceGateway;
import com.trendyol.shoppingservice.infrastructure.repository.DefaultCouponRepository;
import com.trendyol.shoppingservice.infrastructure.repository.DefaultMongoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @Bean
    ShoppingService shoppingService(CommandHandler<CreateItemCommand> createItemCommandCommandHandler, ShoppingRepository shoppingRepository) {
        return new DefaultShoppingService(createItemCommandCommandHandler,shoppingRepository);
    }

    @Bean
    CampaignService campaignService() {
        return new CampaignServiceGateway();
    }

    @Bean
    ProductService productService() {
        return new ProductServiceGateway();
    }

    @Bean
    ValidationService<CreateItemCommand> createItemCommandValidationService() {
        return new CreateItemValidationService();
    }

    @Bean
    CouponRepository couponRepository() {
        return new DefaultCouponRepository();
    }

    @Bean
    public MongoClient getMongoClient(DatabaseConfiguration configuration) {
        final String mongoConnectionString = configuration.getMongoDbConnectionString();
        return MongoClients.create(mongoConnectionString);
    }

    @Bean
    ShoppingRepository shoppingRepository(DatabaseConfiguration databaseConfiguration, MongoClient client) {
        return new DefaultMongoRepository(databaseConfiguration, client);
    }

    @Bean
    CommandHandler<CreateItemCommand> createItemCommandCommandHandler(final ProductService productService,
                                                                      final ShoppingRepository repository,
                                                                      final InitializeCampaignDiscountHandler initializeCampaignDiscountHandler,
                                                                      final InitializeCouponDiscountHandler initializeCouponDiscountHandler,
                                                                      final InitializeDeliveryHandler initializeDeliveryHandler,
                                                                      final InitializeRepositoryHandler initializeRepositoryHandler,
                                                                      final InitializeCartLoggerHandler initializeCartLoggerHandler) {

        return new CreateShoppingCartCommandHandler(productService
                , repository
                , initializeCampaignDiscountHandler
                , initializeCouponDiscountHandler
                , initializeDeliveryHandler
                , initializeRepositoryHandler
                , initializeCartLoggerHandler);
    }

    @Bean
    InitializeCartLoggerHandler initializeCartLoggerHandler() {
        return new InitializeCartLoggerHandler();
    }

    @Bean
    InitializeRepositoryHandler initializeRepositoryHandler(ShoppingRepository shoppingRepository) {
        return new InitializeRepositoryHandler(shoppingRepository);
    }

    @Bean
    InitializeCampaignDiscountHandler initializeCampaignDiscountHandler(CampaignService campaignService) {
        return new InitializeCampaignDiscountHandler(campaignService);
    }

    @Bean
    InitializeCouponDiscountHandler initializeCouponDiscountHandler(CouponRepository couponRepository) {
        return new InitializeCouponDiscountHandler(couponRepository);
    }

    @Bean
    InitializeDeliveryHandler initializeDeliveryHandler() {
        return new InitializeDeliveryHandler();
    }
}
