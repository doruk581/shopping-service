package com.trendyol.shoppingservice.domain.delivery;

import com.trendyol.shoppingservice.domain.Constants;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DeliveryCostCalculatorTest {

    private DeliveryCostCalculator deliveryCostCalculator;


    @Test
    public void controlThatCalculateDeliveryCostReturnCorrectResult() {
        deliveryCostCalculator = new DeliveryCostCalculator(Constants.FIXED_DELIVERY_COST, Constants.COST_PER_PRODUCT, Constants.COST_PER_DELIVERY);

        final BigDecimal expected = deliveryCostCalculator.calculateDeliveryCost(5, 5);

        assertThat(expected, is(equalTo(BigDecimal.valueOf(27.99))));
    }

}