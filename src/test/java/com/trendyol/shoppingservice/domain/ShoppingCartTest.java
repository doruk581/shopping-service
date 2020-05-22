package com.trendyol.shoppingservice.domain;

import com.trendyol.shoppingservice.infrastructure.product.ProductService;
import com.trendyol.shoppingservice.infrastructure.product.ProductServiceGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    private ProductService productService;

    private List<Campaign> campaigns;

    @Before
    public void initiate() {
        shoppingCart = ShoppingCart.create("1");

        final Campaign campaign = Campaign.builder(DiscountType.AMOUNT, BigDecimal.valueOf(5), null, CategoryId.COMPUTER, 3);
        final Campaign campaign3 = Campaign.builder(DiscountType.RATE, null, 0.2, CategoryId.ELECTRONICS, 3);

        campaigns = Arrays.asList(campaign, campaign3);

        productService = new ProductServiceGateway();

    }

    @Test
    public void controlThatAssignCampaignToProductItemsWorksCorrectly() {

        final Category electronics = new Category(CategoryId.ELECTRONICS, null);
        final Category computer = new Category(CategoryId.COMPUTER, electronics);
        final Category notebook = new Category(CategoryId.NOTEBOOK, computer);

        final Product product1 = Product.builder().id("2").category(notebook).price(BigDecimal.valueOf(10000)).title("MACBOOK PRO").build();
        shoppingCart.addItem(product1, 5);

        final ShoppingCartItem item = shoppingCart.getItemList().get(0);

        shoppingCart.assignCampaignToProductItems(campaigns, item);
    }

    @Test
    public void controlThatCreateMethodWorksCorrectly() {
        final ShoppingCart shoppingCart = ShoppingCart.create("111");

        assertThat(shoppingCart.getCartId(), is(equalTo("111")));
        assertNotNull(shoppingCart.getItemList());
        assertThat(shoppingCart.getItemList(), is(equalTo(Collections.emptyList())));
    }

    @Test
    public void controlThatAddItemMethodShouldAddNonExistProductToChart() {
        final ShoppingCart shoppingCart = ShoppingCart.create("111");

        final Category category = Category.builder().category(null).id(CategoryId.COMPUTER).build();

        final Product product = Product.builder().title("MAC").category(category).price(BigDecimal.valueOf(1000)).id("1").build();

        shoppingCart.addItem(product, 10);

        assertThat(shoppingCart.getItemList().size(), is(equalTo(1)));
        assertThat(shoppingCart.getItemList().get(0).getQuantity(), is(equalTo(10)));
        assertNotNull(shoppingCart.getItemList().get(0).getProduct());
        assertThat(shoppingCart.getItemList().get(0).getProduct().getCategory().getId(), is(equalTo(CategoryId.COMPUTER)));
        assertThat(shoppingCart.getItemList().get(0).getProduct().getCategory().getParentCategory(), is(equalTo(null)));
        assertThat(shoppingCart.getItemList().get(0).getProduct().getTitle(), is(equalTo("MAC")));
        assertThat(shoppingCart.getItemList().get(0).getProduct().getPrice(), is(equalTo(BigDecimal.valueOf(1000))));
    }

    @Test
    public void controlThatAddItemMethodShouldIncreaseQuantityIfProductExistInCart() {
        final ShoppingCart shoppingCart = ShoppingCart.create("111");

        final Category category = Category.builder().category(null).id(CategoryId.COMPUTER).build();

        final Product product = Product.builder().title("MAC").category(category).price(BigDecimal.valueOf(1000)).id("1").build();

        shoppingCart.getItemList().add(ShoppingCartItem.builder().quantity(10).product(product).build());

        shoppingCart.addItem(product, 5);

        assertThat(shoppingCart.getItemList().size(), is(equalTo(1)));
        assertThat(shoppingCart.getItemList().get(0).getQuantity(), is(equalTo(15)));

    }

    @Test
    public void controlThatAssignCampaignToProductItemsAssignCorrectCampaigns() {
        final Product product = productService.getProductById("1").get();

        shoppingCart.addItem(product, 5);

        assertThat(shoppingCart.getItemList().size(), is(equalTo(1)));
        shoppingCart.assignCampaignToProductItems(campaigns, shoppingCart.getItemList().get(0));

        final Campaign assignedCampaign = shoppingCart.getItemList().get(0).getProduct().getAppliedCampaign();

        assertNotNull(assignedCampaign);
        assertThat(assignedCampaign.getCategoryId(), is(equalTo(CategoryId.ELECTRONICS)));
        assertThat(assignedCampaign.getMinimumQuantity(), is(equalTo(3)));

    }

    @Test
    public void controlThatAssignCampaignToProductItemsNotAssignACampaignIfNotExist() {
        final Category male = new Category(CategoryId.MALE, null);
        final Product product = Product.builder().category(male).title("TESBİH").price(BigDecimal.TEN).id("10").build();

        shoppingCart.addItem(product, 5);

        assertThat(shoppingCart.getItemList().size(), is(equalTo(1)));
        shoppingCart.assignCampaignToProductItems(new ArrayList<>(), shoppingCart.getItemList().get(0));

        final Campaign assignedCampaign = shoppingCart.getItemList().get(0).getProduct().getAppliedCampaign();

        assertNull(assignedCampaign);
    }

}