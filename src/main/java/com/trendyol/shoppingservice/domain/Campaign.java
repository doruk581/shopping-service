package com.trendyol.shoppingservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Campaign extends Discount {

    private CategoryId categoryId;
    private Integer minimumQuantity;

    public Campaign(DiscountType discountType, BigDecimal discountAmount, Double discountRate, CategoryId categoryId, Integer minimumQuantity) {
        super(discountType, discountAmount, discountRate);
        this.categoryId = categoryId;
        this.minimumQuantity = minimumQuantity;
    }

    public static Campaign builder(DiscountType discountType, BigDecimal discountAmount, Double discountRate, CategoryId categoryId, Integer minimumQuantity) {
        return new Campaign(discountType, discountAmount, discountRate, categoryId, minimumQuantity);
    }

    @Override
    protected Boolean isFeasible(final BigDecimal quantity) {
        return quantity.compareTo(BigDecimal.valueOf(minimumQuantity)) >= 0;
    }


}
