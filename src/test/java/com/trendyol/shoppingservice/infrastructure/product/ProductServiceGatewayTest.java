package com.trendyol.shoppingservice.infrastructure.product;

import com.trendyol.shoppingservice.domain.CategoryId;
import com.trendyol.shoppingservice.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductServiceGatewayTest {

    private ProductServiceGateway productServiceGateway;

    @Before
    public void initiate() {
        productServiceGateway = new ProductServiceGateway();
    }

    @Test
    public void controlThatGetProductByIdShouldReturnOptionalEmptyWhenIdNotExist() {

        final String id = "444";

        final Optional<Product> product = productServiceGateway.getProductById(id);

        assertThat(product.get(), equalTo(Optional.empty()));
    }

    @Test
    public void controlThat() {

        final Optional<Product> product = productServiceGateway.getProductById("1");

        final Set<CategoryId> categoryIds = product.get().getCategory().getBelongingCategories();

        assertTrue(categoryIds.contains(CategoryId.NOTEBOOK));
        assertTrue(categoryIds.contains(CategoryId.COMPUTER));
        assertTrue(categoryIds.contains(CategoryId.ELECTRONICS));
        assertFalse(categoryIds.contains(CategoryId.COSMETIC));

    }

}