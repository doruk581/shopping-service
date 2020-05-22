package com.trendyol.shoppingservice.domain.flow;

import com.trendyol.shoppingservice.domain.ShoppingCart;
import com.trendyol.shoppingservice.domain.ShoppingContext;
import com.trendyol.shoppingservice.domain.ShoppingRepository;
import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InitializeRepositoryHandlerTest {

    @Mock
    private ShoppingRepository shoppingRepository;

    @Mock
    private Handler<ShoppingContext> handler;

    @InjectMocks
    private InitializeRepositoryHandler initializeRepositoryHandler;

    @Test
    public void verifyThatShoppingRepositorySaveMethodCalledOnlyOnce() {

        final ShoppingCart shoppingCart = ShoppingCart.create("111");
        final ShoppingContext context = new ShoppingContext(shoppingCart, new CreateItemCommand());

        initializeRepositoryHandler.handle(context);

        verify(shoppingRepository, times(1)).save(any());
    }

    @Test
    public void verifySuccessorCalledNone() {

        final ShoppingCart shoppingCart = ShoppingCart.create("111");
        final ShoppingContext context = new ShoppingContext(shoppingCart, new CreateItemCommand());

        initializeRepositoryHandler.handle(context);

        verify(handler, times(0)).handle(context);

    }

}