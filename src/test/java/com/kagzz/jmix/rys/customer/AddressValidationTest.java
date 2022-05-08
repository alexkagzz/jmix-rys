package com.kagzz.jmix.rys.customer;

import com.kagzz.jmix.rys.app.entity.Address;
import com.kagzz.jmix.rys.test_support.Validations;
import io.jmix.core.DataManager;
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
    Validations validations;

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
        List<Validations.ValidationResults> violations = validations.validate(address);

//        Then
        assertThat(violations).hasSize(1);
    }
    @Test
    void given_addressWithInvalidStreet_when_validateAddress_then_addressIsInvalidBecauseOfStreet() {

//        Given
        address.setStreet(null);

//        When
       Validations.ValidationResults  streetViolations = validations.validateFirst(address);

//        Then
        assertThat(streetViolations.getAttribute()).isEqualTo("street");
        assertThat(streetViolations.getErrorType())
                .isEqualTo("Enter a valid street name");
    }

}