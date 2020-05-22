package com.trendyol.shoppingservice.domain;

import java.util.Optional;

public interface CouponRepository {

    Optional<Coupon> findCouponById(final String couponId);
}
