package com.kagzz.jmix.rys.customer.screen;

import com.kagzz.jmix.rys.RentYourStuffApplication;
import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.entity.Address;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Table;
import io.jmix.ui.testassist.UiTestAssistConfiguration;
import io.jmix.ui.testassist.junit.UiTest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@UiTest(authenticatedUser = "admin", mainScreenId = "rys_MainScreen", screenBasePackages = "com.kagzz.jmix.rys")
@ContextConfiguration(classes = {RentYourStuffApplication.class, UiTestAssistConfiguration.class})
@AutoConfigureTestDatabase
class CustomerBrowseTest {
    @Autowired
    DataManager dataManager;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = dataManager.create(Customer.class);
        customer.setFirstName("Foo");
        customer.setLastName("Bar");
        customer.setEmail("foo@bar.com");

        Address address = dataManager.create(Address.class);
        address.setCity("Kiambu");
        address.setStreet("Kasarini Street");
        address.setPostalCode("00300");

        customer.setAddress(address);
        dataManager.save(customer);
    }

    @AfterEach
    void tearDown() {
        dataManager.remove(customer);
    }

    @Test
    void given_oneCustomerExists_when_openCustomerBrowse_then_tableContainsTheCustomer(Screens screens) {

//        Given:
        CustomerBrowse customerBrowse = openCustomerBrowse(screens);

//        assertThat(screens.getOpenedScreens().getActiveScreens().stream().findFirst().orElseThrow())
//                .isEqualTo(customerBrowse);
//        assertThat(screens.getOpenedScreens().getActiveScreens().stream().findFirst().orElseThrow())
//                .isInstanceOf(CustomerEdit.class);

//       Expect:
        assertThat(getFirstLoadedCustomer(customerBrowse)).isEqualTo(customer);
    }

    @NotNull
    private Customer getFirstLoadedCustomer(CustomerBrowse customerBrowse) {
        Collection<Customer> customers = loadedCustomers(customerBrowse);

        return customers.stream().findFirst().orElseThrow();
    }

    @NotNull
    private Collection<Customer> loadedCustomers(CustomerBrowse customerBrowse) {
        return getCustomersTable(customerBrowse).getItems().getItems();
    }

    @Nullable
    private Table<Customer> getCustomersTable(CustomerBrowse customerBrowse) {
        return (Table<Customer>) customerBrowse.getWindow().getComponent("customersTable");
    }

    @NotNull
    private CustomerBrowse openCustomerBrowse(Screens screens) {
        CustomerBrowse customerBrowse = screens.create(CustomerBrowse.class);
        customerBrowse.show();
        return customerBrowse;
    }
}