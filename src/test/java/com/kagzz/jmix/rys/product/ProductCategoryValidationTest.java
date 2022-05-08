package com.kagzz.jmix.rys.product;

import com.kagzz.jmix.rys.product.entity.ProductCategory;
import com.kagzz.jmix.rys.test_support.TenantUserEnvironment;
import com.kagzz.jmix.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class ProductCategoryValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations validations;

    private ProductCategory productCategory;

    @BeforeEach
    void setUp() {
         productCategory = dataManager.create(ProductCategory.class);
    }

    @Test
    void given_validProductCategory_when_validate_then_noViolationOccurs() {

//        Given
        productCategory.setName("Foo Category");
        productCategory.setDescription("Foo Category one Desc");

//        When
        List<Validations.ValidationResults>  violations = validations.validate(productCategory);

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
        List<Validations.ValidationResults> violations = validations.validate(productCategory);

//        Then
        assertThat(violations).hasSize(1);

        Validations.ValidationResults  nameViolations = violations.get(0);

        assertThat(nameViolations.getAttribute()).isEqualTo("name");

        assertThat(nameViolations.getErrorType())
                .isEqualTo(validations.validationMessage("NotBlank"));
    }

}