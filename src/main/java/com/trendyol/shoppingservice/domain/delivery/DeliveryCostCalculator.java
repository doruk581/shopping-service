package com.trendyol.shoppingservice.domain.delivery;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class DeliveryCostCalculator {

    private BigDecimal fixedCost;
    private BigDecimal costPerProduct;
    private BigDecimal costPerDelivery;

    public BigDecimal calculateDeliveryCost(final Integer numberOfDeliveries, final Integer numberOfProducts) {
        return this.costPerDelivery.multiply(BigDecimal.valueOf(numberOfDeliveries))
                .add(this.costPerProduct.multiply(BigDecimal.valueOf(numberOfProducts)))
                .add(this.fixedCost);
    }
}
