package com.trendyol.shoppingservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ShoppingCart {

    private String cartId;

    private BigDecimal deliveryCost;
    @BsonIgnore
    private Coupon coupon;

    private BigDecimal campaignDiscount;
    private BigDecimal couponDiscount;
    private BigDecimal totalCostBeforeDiscounts;
    private BigDecimal totalCostAfterCampaignDiscount;
    private BigDecimal totalCostAfterCouponDiscount;

    private List<ShoppingCartItem> itemList;

    private ShoppingCart(String cartId, List<ShoppingCartItem> itemList) {
        this.cartId = cartId;
        this.itemList = itemList;
    }

    public static ShoppingCart create(final String cartId) {
        return new ShoppingCart(cartId, new ArrayList<>());
    }

    public void addItem(final Product product, final Integer quantity) {

        final Optional<ShoppingCartItem> productItem = this.itemList
                .stream()
                .filter(item -> item.getProduct() != null)
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (productItem.isPresent()) {
            productItem.get().increaseQuantity(quantity);
            this.totalCostBeforeDiscounts = calculateTotalCostBeforeDiscounts();
            return;
        }
        this.itemList.add(ShoppingCartItem.builder().product(product).quantity(quantity).build());
        this.totalCostBeforeDiscounts = calculateTotalCostBeforeDiscounts();
    }

    public BigDecimal calculateTotalCostBeforeDiscounts() {
        return this.itemList
                .stream()
                .map(ShoppingCartItem::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void assignCampaignToProductItems(final List<Campaign> campaignList, final ShoppingCartItem item) {

        final Optional<Campaign> maybeCampaign = campaignList.stream()
                .filter(camp -> camp.isFeasible(BigDecimal.valueOf(item.getQuantity())))
                .min(Comparator.comparing(campaign1 -> campaign1.calculate(campaign1.getDiscountType(), item.totalPrice())));

        if (maybeCampaign.isPresent()) {
            final Campaign campaign = maybeCampaign.get();
            item.getProduct().setAppliedCampaign(campaign);
        }

        this.totalCostAfterCampaignDiscount = getCampaignDiscounts();
        this.campaignDiscount = this.totalCostBeforeDiscounts.subtract(this.totalCostAfterCampaignDiscount);
    }

    /*Campaign strategy is get Max discount based one product price and discount from total price*/
    public BigDecimal getCampaignDiscounts() {

        return this.itemList
                .stream()
                .filter(item -> item.getProduct().getAppliedCampaign() != null)
                .map(item -> {
                    final Campaign campaign = item.getProduct().getAppliedCampaign();
                    return campaign.calculate(campaign.getDiscountType(), item.totalPrice());
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /*Coupon strategy is get coupon discount from total price*/
    public void applyCoupon(final Coupon coupon) {
        final Boolean isFeasible = coupon.isFeasible(this.totalCostAfterCampaignDiscount);

        if (isFeasible) {
            this.coupon = coupon;
            this.totalCostAfterCouponDiscount = this.getCouponDiscount();
        }
    }

    public Boolean isCouponAvailable() {
        return this.coupon != null;
    }

    public Integer getNumberOfDeliveries() {
        return this.itemList
                .stream()
                .map(ShoppingCartItem::getProduct)
                .map(Product::getCategory)
                .collect(Collectors.toSet())
                .size();
    }

    public Integer getNumberOfProducts() {
        return this.itemList
                .stream()
                .map(ShoppingCartItem::getQuantity)
                .reduce(0, Integer::sum);
    }

    public BigDecimal totalDiscount() {
        return Optional.ofNullable(this.couponDiscount).orElse(BigDecimal.ZERO).add(Optional.ofNullable(campaignDiscount).orElse(BigDecimal.ZERO));
    }

    public BigDecimal totalAmountOfItems() {
        return this.totalCostBeforeDiscounts.subtract(totalDiscount());
    }

    public BigDecimal getCouponDiscount() {
        if (!isCouponAvailable())
            return BigDecimal.valueOf(0);

        final BigDecimal totalCostAfterCouponDiscount = this.coupon.calculate(this.coupon.getDiscountType(), totalCostAfterCampaignDiscount);
        this.couponDiscount = this.totalCostAfterCampaignDiscount.subtract(totalCostAfterCouponDiscount);

        return totalCostAfterCouponDiscount;
    }


}
