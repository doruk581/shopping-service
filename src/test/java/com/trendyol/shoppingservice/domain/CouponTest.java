package com.trendyol.shoppingservice.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CouponTest {


    @Test
    public void controlThatIsFeasibleMethodShouldReturnFalseWhenAmountSmallerThanMinimumAmount() {
        final Coupon coupon = new Coupon(DiscountType.AMOUNT, BigDecimal.TEN, null, BigDecimal.valueOf(100), "11");

        final Boolean expected = coupon.isFeasible(BigDecimal.valueOf(80));

        assertThat(expected, is(equalTo(Boolean.FALSE)));
    }

    @Test
    public void controlThatIsFeasibleMethodShouldReturnTrueWhenAmountBiggerThanMinimumAmount() {
        final Coupon coupon = new Coupon(DiscountType.AMOUNT, BigDecimal.TEN, null, BigDecimal.valueOf(100), "11");

        final Boolean expected = coupon.isFeasible(BigDecimal.valueOf(120));

        assertThat(expected, is(equalTo(Boolean.TRUE)));
    }

}