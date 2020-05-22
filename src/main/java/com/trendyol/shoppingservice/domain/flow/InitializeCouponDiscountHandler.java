package com.trendyol.shoppingservice.domain.flow;

import com.trendyol.shoppingservice.domain.Coupon;
import com.trendyol.shoppingservice.domain.CouponRepository;
import com.trendyol.shoppingservice.domain.ShoppingContext;

import java.util.Optional;

public class InitializeCouponDiscountHandler implements Handler<ShoppingContext> {

    private Handler<ShoppingContext> successor;
    private CouponRepository couponRepository;

    public InitializeCouponDiscountHandler(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void setSuccessor(Handler<ShoppingContext> handler) {
        this.successor = handler;
    }

    @Override
    public void handle(final ShoppingContext context) {

        final Optional<Coupon> maybeCoupon = couponRepository.findCouponById(context.getCreateItemCommand().getCouponId());
        maybeCoupon.ifPresent(coupon -> context.getShoppingCart().applyCoupon(coupon));

        if (successor != null) successor.handle(context);
    }
}
