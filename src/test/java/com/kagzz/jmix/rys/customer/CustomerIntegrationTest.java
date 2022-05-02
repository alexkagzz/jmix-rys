package com.kagzz.jmix.rys.customer;

import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.entity.Address;
import com.kagzz.jmix.rys.test_support.DatabaseCleanup;
import com.kagzz.jmix.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerIntegrationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    @Autowired
    DatabaseCleanup databaseCleanup;

    private Customer customer;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(Customer.class);
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

}