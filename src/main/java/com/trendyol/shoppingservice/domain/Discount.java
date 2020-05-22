package com.trendyol.shoppingservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public abstract class Discount {

    private DiscountType discountType;
    private BigDecimal discountAmount;
    private Double discountRate;

    public Discount(DiscountType discountType, BigDecimal discountAmount, Double discountRate) {
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
    }

    protected abstract Boolean isFeasible(final BigDecimal quantity);

    protected BigDecimal calculateAmountByDiscount(final BigDecimal amount) {

        final BigDecimal result = amount.subtract(this.discountAmount);

        return result.intValue() < 0 ? BigDecimal.ZERO : result;
    }

    protected BigDecimal calculateAmountByDiscountRate(final BigDecimal amount) {

        final BigDecimal rateAmount = amount.multiply(BigDecimal.valueOf(discountRate)).divide(Constants.PERCENT,2);


        return amount.subtract(rateAmount);
    }

    public BigDecimal calculate(final DiscountType discountType, final BigDecimal amount) {

        switch (discountType) {
            case AMOUNT:
                return calculateAmountByDiscount(amount);
            case RATE:
                return calculateAmountByDiscountRate(amount);
            default:
                throw new IllegalArgumentException();
        }
    }

}
