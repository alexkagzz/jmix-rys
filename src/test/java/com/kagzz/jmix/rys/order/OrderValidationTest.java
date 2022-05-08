package com.kagzz.jmix.rys.order;

import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.order.entity.Order;
import com.kagzz.jmix.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations validations;

    private Order order;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = TODAY.minusDays(1);

    @BeforeEach
    void setUp() {
        order = dataManager.create(Order.class);
    }

    @Test
    void given_validOrder_when_validate_then_noViolationOccurs() {

//        Given
        order.setOrderDate(TODAY);
        order.setCustomer(dataManager.create(Customer.class));

//        When
        List<Validations.ValidationResults>  violations = validations.validate(order);

//        Then
        assertThat(violations).isEmpty();
    }

    @Test
    void given_orderWithoutDate_when_validate_then_oneViolationOccurs() {

//        Given
        order.setOrderDate(null);
        order.setCustomer(dataManager.create(Customer.class));

//        When
        List<Validations.ValidationResults> violations = validations.validate(order);

//        Then
        assertThat(violations).hasSize(1);

        Validations.ValidationResults productViolations = violations.get(0);

        assertThat(productViolations.getAttribute()).isEqualTo("orderDate");

        assertThat(productViolations.getErrorType())
                .isEqualTo(validations.validationMessage("NotNull"));
    }

    @Test
    void given_orderWithDateInThePast_when_validate_then_oneViolationOccurs() {

//        Given
        order.setOrderDate(YESTERDAY);
        order.setCustomer(dataManager.create(Customer.class));

//        When
        List<Validations.ValidationResults> violations = validations.validate(order);

//        Then
        assertThat(violations).hasSize(1);

        Validations.ValidationResults productViolations = violations.get(0);

        assertThat(productViolations.getAttribute()).isEqualTo("orderDate");

        assertThat(productViolations.getErrorType())
                .isEqualTo(validations.validationMessage("FutureOrPresent"));
    }


    @Test
    void given_orderWithoutCustomer_when_validate_then_oneViolationOccurs() {

//        Given
        order.setOrderDate(TODAY);
        order.setCustomer(null);

//        When
        List<Validations.ValidationResults> violations = validations.validate(order);

//        Then
        assertThat(violations).hasSize(1);

        Validations.ValidationResults productViolations = violations.get(0);

        assertThat(productViolations.getAttribute()).isEqualTo("customer");

        assertThat(productViolations.getErrorType())
                .isEqualTo(validations.validationMessage("NotNull"));
    }

}