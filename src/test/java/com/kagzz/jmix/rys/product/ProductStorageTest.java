package com.kagzz.jmix.rys.product;

import com.kagzz.jmix.rys.product.entity.PriceUnit;
import com.kagzz.jmix.rys.product.entity.Product;
import com.kagzz.jmix.rys.product.entity.ProductPrice;
import com.kagzz.jmix.rys.test_support.DatabaseCleanup;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.security.SystemAuthenticator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductStorageTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    @Autowired
    DatabaseCleanup databaseCleanup;

    private Product product;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(ProductPrice.class);
        databaseCleanup.removeAllEntities(Product.class);
        product = dataManager.create(Product.class);
    }

    @Test
    void given_validProduct_when_saveProduct_then_productIsSaved() {

//        Given
        product.setName("Prod One");
        product.setDescription("Description for prod One");

//        When
         Product savedProduct = systemAuthenticator.withSystem(() -> dataManager.save(product));

//        Then
        assertThat(savedProduct.getId()).isNotNull();
    }

    @Test
    void given_validProductWithPrices_when_saveProduct_then_productAndPricesAreSaved() {

//        Given
        product.setName("Prod One");
        product.setDescription("Description for prod One");

        ProductPrice pricePerDay = createProductPrice(PriceUnit.DAY, 5, product);

        ProductPrice pricePerWeek = createProductPrice(PriceUnit.WEEK, 30, product);

        ProductPrice pricePerMonth = createProductPrice(PriceUnit.MONTH, 100, product);

        product.setPrices(List.of(pricePerDay, pricePerWeek, pricePerMonth));

//        When
        Optional<Product> savedProduct = systemAuthenticator.withSystem(() -> {
            dataManager.save(product, pricePerDay, pricePerWeek, pricePerMonth);
            return dataManager.load(Id.of(product)).optional();
        });

//        Then
        assertThat(savedProduct).isPresent();
    }

    @NotNull
    private ProductPrice createProductPrice(PriceUnit unit, int val, Product product) {
        ProductPrice pricePerWeek = dataManager.create(ProductPrice.class);
        pricePerWeek.setProduct(product);
        pricePerWeek.setUnit(unit);
        pricePerWeek.setAmount(BigDecimal.valueOf(val));
        return pricePerWeek;
    }


}