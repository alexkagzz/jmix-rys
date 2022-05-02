package com.kagzz.jmix.rys.product;

import com.kagzz.jmix.rys.product.entity.Product;
import com.kagzz.jmix.rys.product.entity.ProductCategory;
import com.kagzz.jmix.rys.test_support.DatabaseCleanup;
import com.kagzz.jmix.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductCategoryValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification validationVerification;

    @Autowired
    DatabaseCleanup databaseCleanup;

    private ProductCategory productCategory;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(ProductCategory.class);
        productCategory = dataManager.create(ProductCategory.class);
    }

    @Test
    void given_validProductCategory_when_validate_then_noViolationOccurs() {

//        Given
        productCategory.setName("Foo Category");
        productCategory.setDescription("Foo Category one Desc");

//        When
        List<ValidationVerification.ValidationResults>  violations = validationVerification.validate(productCategory);

//        Then
        assertThat(violations).isEmpty();
    }

    @NullSource
    @EmptySource
    @ParameterizedTest
    void given_productCategoryWithoutName_when_validate_then_oneViolationOccurs(String name) {

//        Given
        productCategory.setName(name);
        productCategory.setDescription("Foo Category one Desc");

//        When
        List<ValidationVerification.ValidationResults> violations = validationVerification.validate(productCategory);

//        Then
        assertThat(violations).hasSize(1);

        ValidationVerification.ValidationResults  nameViolations = violations.get(0);

        assertThat(nameViolations.getAttribute()).isEqualTo("name");

        assertThat(nameViolations.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotBlank"));
    }

}