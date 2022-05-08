package com.kagzz.jmix.rys.order;

import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.order.entity.Order;
import com.kagzz.jmix.rys.test_support.TenantUserEnvironment;
import io.jmix.core.DataManager;
import io.jmix.core.MetadataTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class OrderInstanceNameTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    MetadataTools metadataTools;

    private Order order;

    @BeforeEach
    void setUp() {
        order = dataManager.create(Order.class);
    }

    @Test
    void given_orderContainsCustomerAndOrderDate_expect_instanceNameContainsFormattedValues() {

//        Given
        Customer customer = dataManager.create(Customer.class);
        customer.setFirstName("Alex");
        customer.setLastName("Bar");
        customer.setEmail("alex@bar.com");
        order.setCustomer(customer);

//        When
        order.setOrderDate(LocalDate.parse("2022-03-23"));

//        Expect
        assertThat(metadataTools.getInstanceName(order))
                .isEqualTo("Alex Bar - 23/03/2022");
    }


}