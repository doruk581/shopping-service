package com.trendyol.shoppingservice.domain;

import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;
import com.trendyol.shoppingservice.domain.flow.*;
import com.trendyol.shoppingservice.infrastructure.product.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateShoppingCartCommandHandlerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ShoppingRepository shoppingRepository;

    @Mock
    private InitializeCampaignDiscountHandler initializeCampaignDiscountHandler;

    @Mock
    private InitializeCouponDiscountHandler initializeCouponDiscountHandler;

    @Mock
    private InitializeDeliveryHandler initializeDeliveryHandler;

    @Mock
    private InitializeRepositoryHandler initializeRepositoryHandler;

    @Mock
    private InitializeCartLoggerHandler initializeCartLoggerHandler;

    @InjectMocks
    private CreateShoppingCartCommandHandler createShoppingCartCommandHandler;


    @Test(expected = ProductNotExistException.class)
    public void controlThatHandleMethodShouldThrowProductNotExistExceptionWhenProductIdNotExist() {
        when(productService.getProductById(any())).thenReturn(Optional.empty());

        createShoppingCartCommandHandler.handle(new CreateItemCommand());
    }

    @Test
    public void controlThatWhenChartExistInDatabaseThenVerifyInitializeCampaignDiscountHandlerCall() {
        final Product product = Product.builder().build();
        final Optional<ShoppingCart> shoppingCart = Optional.of(ShoppingCart.create("111"));

        when(productService.getProductById(any())).thenReturn(Optional.of(product));
        when(shoppingRepository.findByCartId(anyString())).thenReturn(shoppingCart);

        createShoppingCartCommandHandler.handle(new CreateItemCommand());

        verify(initializeCampaignDiscountHandler, times(1)).handle(any());
    }

    @Test
    public void controlThatSuccessorMethodsCalled() {

        final Product product = new Product();
        final ShoppingCart shoppingCart = ShoppingCart.create("111");

        when(productService.getProductById(any())).thenReturn(Optional.of(product));
        when(shoppingRepository.findByCartId(anyString())).thenReturn(Optional.of(shoppingCart));

        createShoppingCartCommandHandler.handle(new CreateItemCommand());

        verify(initializeCampaignDiscountHandler, times(1)).setSuccessor(any());
        verify(initializeCouponDiscountHandler, times(1)).setSuccessor(any());
        verify(initializeDeliveryHandler, times(1)).setSuccessor(any());
        verify(initializeCartLoggerHandler, times(1)).setSuccessor(any());
    }
}