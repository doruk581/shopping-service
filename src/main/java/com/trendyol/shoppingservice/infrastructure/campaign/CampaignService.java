package com.trendyol.shoppingservice.infrastructure.campaign;

import com.trendyol.shoppingservice.domain.Campaign;
import com.trendyol.shoppingservice.domain.ShoppingCartItem;

import java.util.List;

/*
 * Consider Campaign is another domain service
 * */
public interface CampaignService {

    List<Campaign> findCampaign(final ShoppingCartItem item);
}
