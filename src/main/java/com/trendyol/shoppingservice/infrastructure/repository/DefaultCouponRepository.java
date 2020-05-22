package com.trendyol.shoppingservice.infrastructure.repository;

import com.trendyol.shoppingservice.domain.Coupon;
import com.trendyol.shoppingservice.domain.CouponRepository;
import com.trendyol.shoppingservice.domain.DiscountType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultCouponRepository implements CouponRepository {

    private static final Map<String, Coupon> couponMap = new HashMap<>();

    static {
        final Coupon coupon1 = new Coupon(DiscountType.RATE, null, 0.1, BigDecimal.valueOf(100), "COUP1");
        final Coupon coupon2 = new Coupon(DiscountType.AMOUNT, BigDecimal.valueOf(100), null, BigDecimal.valueOf(1000), "COUP2");

        couponMap.put(coupon1.getCouponCode(), coupon1);
        couponMap.put(coupon2.getCouponCode(), coupon2);
    }

    @Override
    public Optional<Coupon> findCouponById(String couponId) {
        return Optional.ofNullable(couponMap.get(couponId));
    }
}
