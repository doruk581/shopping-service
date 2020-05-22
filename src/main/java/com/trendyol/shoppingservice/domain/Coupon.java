package com.trendyol.shoppingservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Coupon extends Discount {

    private String couponCode;
    private BigDecimal discountAmount;
    private BigDecimal minimumAmount;

    public Coupon(DiscountType discountType, BigDecimal discountAmount, Double discountRate, BigDecimal minimumAmount, String couponCode) {
        super(discountType, discountAmount, discountRate);
        this.minimumAmount = minimumAmount;
        this.couponCode = couponCode;
    }

    @Override
    protected Boolean isFeasible(final BigDecimal amount) {
        return !(amount.compareTo(minimumAmount) < 0);
    }
}
