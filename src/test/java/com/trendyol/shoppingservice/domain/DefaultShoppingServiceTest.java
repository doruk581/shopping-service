package com.trendyol.shoppingservice.domain;

import com.trendyol.shoppingservice.domain.commands.CommandHandler;
import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultShoppingServiceTest {

    @Mock
    private ShoppingRepository shoppingRepository;

    @Mock
    private CommandHandler<CreateItemCommand> createItemCommandCommandHandler;

    @InjectMocks
    private DefaultShoppingService shoppingService;


    @Test(expected = CartNotFoundException.class)
    public void controlThatGetShoppingCartThrowCartNotFoundExceptionWhenCartNotExistInDatabase() {

        when(shoppingRepository.findByCartId(any())).thenReturn(Optional.empty());

        shoppingService.getShoppingCart("111");
    }

    @Test
    public void controlThatCreateMethodCalledAtLeastOnce() {
        final CreateItemCommand createItemCommand = new CreateItemCommand();

        shoppingService.create(createItemCommand);

        verify(createItemCommandCommandHandler, times(1)).handle(createItemCommand);
    }
}