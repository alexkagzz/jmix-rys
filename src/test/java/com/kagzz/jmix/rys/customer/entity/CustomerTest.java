package com.kagzz.jmix.rys.customer.entity;

import com.kagzz.jmix.rys.entity.Address;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerIntegrationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    @Autowired
    Validator validator;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = dataManager.create(Customer.class);
    }

    @Test
    void given_validCustomer_when_saveCustomer_then_customerIsSaved() {

//        Given
        customer.setFirstName("Foo");
        customer.setLastName("Bar");
        customer.setEmail("foo@bar.com");

        Address address = dataManager.create(Address.class);
        address.setCity("Kiambu");
        address.setStreet("Kasarini");
        address.setPostalCode("00300");

        customer.setAddress(address);

//        When
        Customer savedCustomer = systemAuthenticator.withSystem(() -> dataManager.save(customer));

//        Then
        assertThat(savedCustomer.getId())
                .isNotNull();
    }

    @Test
    void given_customerWithInvalidEmail_when_validateCustomer_then_customerIsInvalid() {

//        Given
        customer.setEmail("invalidEmailAddress");

//        When
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer, Default.class);

//        Then
        assertThat(violations).hasSize(1);

//        And
        assertThat(getViolation(violations).getPropertyPath().toString()).isEqualTo("email");
        assertThat(getViolation(violations).getMessageTemplate())
                .isEqualTo("{javax.validation.constraints.Email.message}");
    }

    private ConstraintViolation<Customer> getViolation(Set<ConstraintViolation<Customer>> violations) {
        return violations.stream().findFirst().orElseThrow();
    }

}