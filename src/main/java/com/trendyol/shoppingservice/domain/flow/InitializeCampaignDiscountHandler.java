package com.trendyol.shoppingservice.domain.flow;

import com.trendyol.shoppingservice.domain.Campaign;
import com.trendyol.shoppingservice.domain.ShoppingCart;
import com.trendyol.shoppingservice.domain.ShoppingContext;
import com.trendyol.shoppingservice.infrastructure.campaign.CampaignService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class InitializeCampaignDiscountHandler implements Handler<ShoppingContext> {

    private Handler<ShoppingContext> successor;

    private CampaignService campaignService;

    public InitializeCampaignDiscountHandler(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @Override
    public void setSuccessor(Handler<ShoppingContext> handler) {
        this.successor = handler;
    }

    @Override
    public void handle(final ShoppingContext context) {

        final ShoppingCart shoppingCart = context.getShoppingCart();

        shoppingCart.getItemList().forEach(item -> {
            final List<Campaign> campaignsAssignedToCategory = campaignService.findCampaign(item);

            shoppingCart.assignCampaignToProductItems(campaignsAssignedToCategory, item);
        });


        if (successor != null) successor.handle(context);
    }
}
