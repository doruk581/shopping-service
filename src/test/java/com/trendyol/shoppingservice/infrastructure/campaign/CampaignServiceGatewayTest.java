package com.trendyol.shoppingservice.infrastructure.campaign;

import com.trendyol.shoppingservice.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;

public class CampaignServiceGatewayTest {

    private CampaignServiceGateway campaignServiceGateway;

    @Before
    public void initiate() {
        campaignServiceGateway = new CampaignServiceGateway();
    }

    @Test
    public void controlThatFindCampaignMethodShouldReturnEmptyListWhenNoCampaignExist() {
        final Category category = Category.builder().id(CategoryId.MALE).category(null).build();

        final Product product = Product.builder().id("11").price(BigDecimal.valueOf(10)).category(category).title("ERKEKSEL").build();

        final ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder().quantity(1).product(product).build();

        final List<Campaign> campaignList = campaignServiceGateway.findCampaign(shoppingCartItem);

        assertNotNull(campaignList);
        assertThat(campaignList, is(equalTo(Collections.emptyList())));
    }

    @Test
    public void controlThatFindCampaignMethodShouldReturnCorrectListWhenCampaignExist() {
        final Category category = Category.builder().id(CategoryId.COMPUTER).category(null).build();

        final Product product = Product.builder().id("11").price(BigDecimal.valueOf(10)).category(category).title("MAC").build();

        final ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder().quantity(1).product(product).build();

        final List<Campaign> campaignList = campaignServiceGateway.findCampaign(shoppingCartItem);

        assertNotNull(campaignList);
        assertThat(campaignList.size(), is(equalTo(1)));
    }

}