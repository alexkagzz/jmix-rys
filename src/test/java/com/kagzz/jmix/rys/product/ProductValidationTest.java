package com.kagzz.jmix.rys.product;

import com.kagzz.jmix.rys.product.entity.Product;
import com.kagzz.jmix.rys.test_support.TenantUserEnvironment;
import com.kagzz.jmix.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class ProductValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations validations;

    private Product product;

    @BeforeEach
    void setUp() {
        product = dataManager.create(Product.class);
    }

    @Test
    void given_productWithoutName_when_validate_then_oneViolationOccurs() {

//        Given
        product.setName(null);

//        When
        List<Validations.ValidationResults> violations = validations.validate(product);

//        Then
        assertThat(violations).hasSize(1);
    }

    @Test
    void given_validProduct_when_validate_then_noViolationOccurs() {

//        Given
        product.setName("Prod Two");

//        When
        List<Validations.ValidationResults>  violations = validations.validate(product);

//        Then
        assertThat(violations).isEmpty();
    }

}