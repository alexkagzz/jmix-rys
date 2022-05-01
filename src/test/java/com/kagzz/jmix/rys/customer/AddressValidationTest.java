package com.kagzz.jmix.rys.customer;

import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.entity.Address;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AddressValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification validationVerification;

    private Address address;

    @BeforeEach
    void setUp() {
        address = dataManager.create(Address.class);
    }


    @Test
    void given_addressWithInvalidStreet_when_validateAddress_then_oneViolationOccurs() {

//        Given
        address.setStreet(null);

//        When
        List<ValidationVerification.ValidationResults> violations = validationVerification.validate(address);

//        Then
        assertThat(violations).hasSize(1);
    }
    @Test
    void given_addressWithInvalidStreet_when_validateAddress_then_addressIsInvalidBecauseOfStreet() {

//        Given
        address.setStreet(null);

//        When
       ValidationVerification.ValidationResults  streetViolations = validationVerification.validateFirst(address);

//        Then
        assertThat(streetViolations.getAttribute()).isEqualTo("street");
        assertThat(streetViolations.getErrorType())
                .isEqualTo("Enter a valid street name");
    }

}