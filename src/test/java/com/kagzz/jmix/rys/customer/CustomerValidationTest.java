package com.kagzz.jmix.rys.customer;

import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.test_support.TenantUserEnvironment;
import com.kagzz.jmix.rys.test_support.Validations;
import com.kagzz.jmix.rys.test_support.test_data.Customers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class CustomerValidationTest {

    @Autowired
    Validations validations;

    @Autowired
    Customers customers;


    @Test
    void given_validCustomer_expect_noViolationOccurs() {

        // given
        Customer customer = customers.save(
                customers.defaultData()
                        .email("valid@email.address")
                        .build()
        );

        // expect
        validations.assertNoViolations(customer);
    }

    @Test
    void given_customerWithInvalidEmail_when_validateCustomer_then_customerIsInvalidBecauseOfEmail() {

//        Given
        Customer customer = customers.save(
                customers.defaultData()
                        .email("invalidEmailAddress")
                        .build()
        );

//        Then
        validations.assertExactlyOneViolationWith(customer, "email", "Email");
    }

}