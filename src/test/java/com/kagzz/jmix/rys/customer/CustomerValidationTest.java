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
class CustomerValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    @Autowired
    ValidationVerification validationVerification;


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
    void given_customerWithInvalidEmail_when_validateCustomer_then_oneViolationOccurs() {

//        Given
        customer.setLastName("Bar");
        customer.setEmail("invalidEmailAddress");

//        When
        List<ValidationVerification.ValidationResults> violations = validationVerification.validate(customer);

//        Then
        assertThat(violations).hasSize(1);
    }
    @Test
    void given_customerWithInvalidEmail_when_validateCustomer_then_customerIsInvalidBecauseOfEmail() {

//        Given
        customer.setLastName("Bar");
        customer.setEmail("invalidEmailAddress");

//        When
       ValidationVerification.ValidationResults  emailViolations = validationVerification.validateFirst(customer);

//        Then
        assertThat(emailViolations.getAttribute()).isEqualTo("email");
        assertThat(emailViolations.getErrorType())
                .isEqualTo(validationVerification.validationMessage("Email"));
    }

}