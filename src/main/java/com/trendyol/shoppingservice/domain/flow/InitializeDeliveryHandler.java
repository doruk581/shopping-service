package com.trendyol.shoppingservice.domain.flow;

import com.trendyol.shoppingservice.domain.Constants;
import com.trendyol.shoppingservice.domain.ShoppingCart;
import com.trendyol.shoppingservice.domain.ShoppingContext;
import com.trendyol.shoppingservice.domain.delivery.DeliveryCostCalculator;

import java.math.BigDecimal;

public class InitializeDeliveryHandler implements Handler<ShoppingContext> {

    private Handler<ShoppingContext> successor;

    @Override
    public void setSuccessor(Handler<ShoppingContext> handler) {
        this.successor = handler;
    }

    @Override
    public void handle(final ShoppingContext context) {

        final ShoppingCart shoppingCart = context.getShoppingCart();

        final DeliveryCostCalculator deliveryCostCalculator = DeliveryCostCalculator.builder()
                .costPerDelivery(Constants.COST_PER_DELIVERY)
                .costPerProduct(Constants.COST_PER_PRODUCT)
                .fixedCost(Constants.FIXED_DELIVERY_COST)
                .build();

        final BigDecimal deliveryCost = deliveryCostCalculator.calculateDeliveryCost(shoppingCart.getNumberOfDeliveries(), shoppingCart.getNumberOfProducts());

        shoppingCart.setDeliveryCost(deliveryCost);

        if (successor != null) successor.handle(context);
    }
}
