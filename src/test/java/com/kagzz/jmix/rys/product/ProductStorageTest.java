package com.kagzz.jmix.rys.product;

import com.kagzz.jmix.rys.entity.Currency;
import com.kagzz.jmix.rys.entity.Money;
import com.kagzz.jmix.rys.product.entity.PriceUnit;
import com.kagzz.jmix.rys.product.entity.Product;
import com.kagzz.jmix.rys.product.entity.ProductCategory;
import com.kagzz.jmix.rys.product.entity.ProductPrice;
import com.kagzz.jmix.rys.test_support.DatabaseCleanup;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
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
        databaseCleanup.removeAllEntities(ProductCategory.class);
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

    @Test
    void given_validProductWithCategory_when_saveProduct_then_productAndCategoryAreSaved() {

//        Given
        product.setName("Prod One");
        product.setDescription("Description for prod One");

//        And
        ProductCategory savedProductCategory = saveProductCategory("Foo Category One");

        product.setCategory(savedProductCategory);

//        When
        Optional<Product> savedProduct = systemAuthenticator.withSystem(() -> {
            dataManager.save(product);
            return loadProductWithCategory(product);
        });

//        Then
        assertThat(savedProduct)
                .isPresent()
                .get()
                .extracting("category")
                .isEqualTo(savedProductCategory);
    }

    @NotNull
    private ProductPrice createProductPrice(PriceUnit unit, int val, Product product) {
        Money money = dataManager.create(Money.class);
        money.setAmount(BigDecimal.valueOf(val));
        money.setCurrency(Currency.KES);

        ProductPrice pricePerWeek = dataManager.create(ProductPrice.class);
        pricePerWeek.setProduct(product);
        pricePerWeek.setUnit(unit);
        pricePerWeek.setPrice(money);
        return pricePerWeek;
    }

    @NotNull
    private Optional<Product> loadProductWithCategory(Product product) {
        return dataManager.load(Id.of(product)).fetchPlan(productFPB -> {
                    productFPB.addFetchPlan(FetchPlan.BASE);
                    productFPB.add("category", categoryFPB -> categoryFPB.addFetchPlan(FetchPlan.BASE));
                })
                .optional();
    }

    @NotNull
    private ProductCategory saveProductCategory(String name) {
        ProductCategory productCategory = dataManager.create(ProductCategory.class);
        productCategory.setName(name);
        return systemAuthenticator.withSystem(() -> dataManager.save(productCategory));
    }


}