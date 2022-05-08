package com.kagzz.jmix.rys.customer;

import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.app.entity.Address;
import com.kagzz.jmix.rys.test_support.TenantUserEnvironment;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class CustomerIntegrationTest {

    @Autowired
    DataManager dataManager;

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
        Customer savedCustomer =  dataManager.save(customer);

//        Then
        assertThat(savedCustomer.getId())
                .isNotNull();
    }

}