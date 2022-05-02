package com.kagzz.jmix.rys.product;

import com.kagzz.jmix.rys.entity.Currency;
import com.kagzz.jmix.rys.entity.Money;
import com.kagzz.jmix.rys.product.entity.PriceUnit;
import com.kagzz.jmix.rys.product.entity.Product;
import com.kagzz.jmix.rys.product.entity.ProductPrice;
import com.kagzz.jmix.rys.test_support.DatabaseCleanup;
import com.kagzz.jmix.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductPriceValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification validationVerification;

    @Autowired
    DatabaseCleanup databaseCleanup;

    private ProductPrice productPrice;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(ProductPrice.class);
        productPrice = dataManager.create(ProductPrice.class);
    }

    @Test
    void given_validProductPrice_when_validate_then_noViolationOccurs() {

//        Given
        Money money = dataManager.create(Money.class);
        money.setAmount(BigDecimal.valueOf(5));
        money.setCurrency(Currency.KES);
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setPrice(money);
        productPrice.setProduct(dataManager.create(Product.class));

//        When
        List<ValidationVerification.ValidationResults> violations = validationVerification.validate(productPrice);

//        Then
        assertThat(violations).isEmpty();
    }

    @Test
    void given_productPriceWithoutUnit_when_validate_then_oneViolationOccurs() {

//        Given
        Money money = dataManager.create(Money.class);
        money.setAmount(BigDecimal.valueOf(5));
        money.setCurrency(Currency.KES);
        productPrice.setPrice(money);
        productPrice.setProduct(dataManager.create(Product.class));

//        When
        List<ValidationVerification.ValidationResults>  violations = validationVerification.validate(productPrice);

//        Then
        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResults  unitViolations = violations.get(0);

        assertThat(unitViolations.getAttribute()).isEqualTo("unit");

        assertThat(unitViolations.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_productPriceWithoutAmount_when_validate_then_oneViolationOccurs() {

//        Given
        Money money = dataManager.create(Money.class);
        money.setAmount(null);
        money.setCurrency(Currency.KES);

        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setPrice(money);
        productPrice.setProduct(dataManager.create(Product.class));

//        When
        List<ValidationVerification.ValidationResults>  violations = validationVerification.validate(productPrice);

//        Then
        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResults  amountViolations = violations.get(0);

        assertThat(amountViolations.getAttribute()).isEqualTo("price.amount");

        assertThat(amountViolations.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_productPriceWithNegativeAmount_when_validate_then_oneViolationOccurs() {

//        Given
        Money money = dataManager.create(Money.class);
        money.setAmount(BigDecimal.valueOf(-5));
        money.setCurrency(Currency.KES);
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setPrice(money);
        productPrice.setProduct(dataManager.create(Product.class));

//        When
        List<ValidationVerification.ValidationResults>  violations = validationVerification.validate(productPrice);

//        Then
        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResults negativeAmountViolations = violations.get(0);

        assertThat(negativeAmountViolations.getAttribute()).isEqualTo("price.amount");

        assertThat(negativeAmountViolations.getErrorType())
                .isEqualTo(validationVerification.validationMessage("PositiveOrZero"));
    }

    @Test
    void given_productPriceWithoutProduct_when_validate_then_oneViolationOccurs() {

//        Given
        Money money = dataManager.create(Money.class);
        money.setAmount(BigDecimal.valueOf(5));
        money.setCurrency(Currency.KES);
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setPrice(money);

//        When
        List<ValidationVerification.ValidationResults>  violations = validationVerification.validate(productPrice);

//        Then
        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResults productViolations = violations.get(0);

        assertThat(productViolations.getAttribute()).isEqualTo("product");

        assertThat(productViolations.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

}