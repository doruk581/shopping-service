package com.trendyol.shoppingservice.infrastructure.product;

import com.trendyol.shoppingservice.domain.Category;
import com.trendyol.shoppingservice.domain.CategoryId;
import com.trendyol.shoppingservice.domain.Product;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProductServiceGateway implements ProductService {

    private static final List<Product> productList = new ArrayList<>();

    static {

        final Category electronics = new Category(CategoryId.ELECTRONICS, null);
        final Category computer = new Category(CategoryId.COMPUTER, electronics);
        final Category notebook = new Category(CategoryId.NOTEBOOK, computer);
        final Category male = new Category(CategoryId.MALE, null);
        final Category shoes = new Category(CategoryId.SHOE, male);
        final Category clothing = new Category(CategoryId.CLOTHING, male);
        final Category cosmetic = new Category(CategoryId.COSMETIC, male);
        final Category supermarket = new Category(CategoryId.SUPERMARKET, null);
        final Category petshop = new Category(CategoryId.PETSHOP, supermarket);
        final Category homeCare = new Category(CategoryId.HOME_CARE_CLEANING, supermarket);

        final Product product = Product.builder().id("1").price(BigDecimal.valueOf(1000)).category(notebook).title("DELL XPS").build();
        final Product product1 = Product.builder().id("2").category(notebook).price(BigDecimal.valueOf(10000)).title("MACBOOK PRO").build();
        final Product product2 = Product.builder().id("3").category(clothing).price(BigDecimal.valueOf(100)).title("GÖMLEK").build();

        productList.add(product);
        productList.add(product1);
        productList.add(product2);
    }

    @Override
    public Optional<Product> getProductById(String id) {

        return productList
                .stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }
}
