package com.trendyol.shoppingservice.infrastructure.campaign;

import com.trendyol.shoppingservice.domain.Campaign;
import com.trendyol.shoppingservice.domain.CategoryId;
import com.trendyol.shoppingservice.domain.DiscountType;
import com.trendyol.shoppingservice.domain.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CampaignServiceGateway implements CampaignService {

    private final static List<Campaign> campaignList = new ArrayList<>();

    static {
        final Campaign campaign = Campaign.builder(DiscountType.AMOUNT, BigDecimal.valueOf(5), null, CategoryId.COMPUTER, 3);

        final Campaign campaign1 = Campaign.builder(DiscountType.RATE, null, 0.2, CategoryId.SUPERMARKET, 3);

        final Campaign campaign2 = Campaign.builder(DiscountType.RATE, null, 0.5, CategoryId.CLOTHING, 5);

        final Campaign campaign3 = Campaign.builder(DiscountType.RATE, null, 0.4, CategoryId.ELECTRONICS, 3);

        campaignList.add(campaign);
        campaignList.add(campaign1);
        campaignList.add(campaign2);
        campaignList.add(campaign3);

    }

    @Override
    public List<Campaign> findCampaign(ShoppingCartItem item) {

        final Set<CategoryId> categories = item.getProduct().getCategory().getBelongingCategories();

        return campaignList
                .stream()
                .filter(campaign -> categories.contains(campaign.getCategoryId()))
                .collect(Collectors.toList());
    }
}
