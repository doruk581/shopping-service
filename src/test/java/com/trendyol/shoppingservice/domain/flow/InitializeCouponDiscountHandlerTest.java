package com.trendyol.shoppingservice.domain.flow;

import com.trendyol.shoppingservice.domain.CouponRepository;
import com.trendyol.shoppingservice.domain.ShoppingCart;
import com.trendyol.shoppingservice.domain.ShoppingContext;
import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InitializeCouponDiscountHandlerTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private Handler<ShoppingContext> handler;

    @InjectMocks
    private InitializeCouponDiscountHandler initializeCouponDiscountHandler;

    @Mock
    private ShoppingCart cart;


    @Test
    public void whenThereIsNoCouponThenApplyCouponNeverCalled() {

        final ShoppingCart shoppingCart = ShoppingCart.create("111");
        final ShoppingContext context = new ShoppingContext(shoppingCart, new CreateItemCommand());


        when(couponRepository.findCouponById(any())).thenReturn(Optional.empty());

        initializeCouponDiscountHandler.handle(context);

        verify(cart, times(0)).applyCoupon(any());

    }
}