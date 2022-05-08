package com.kagzz.jmix.rys.customer.screen;

import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.app.entity.Address;
import com.kagzz.jmix.rys.test_support.ui.ScreenInteractions;
import com.kagzz.jmix.rys.test_support.ui.TableInteractions;
import com.kagzz.jmix.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerBrowseTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    private Customer customer;

    @BeforeEach
    void setUp() {
        createInitialCustomer();
    }

    private void createInitialCustomer() {
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

    @Test
    void given_oneCustomerExists_when_openCustomerBrowse_then_tableContainsTheCustomer(Screens screens) {

//        Given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        CustomerBrowse customerBrowse = screenInteractions.open(CustomerBrowse.class);
        TableInteractions<Customer> customerTable = customerTable(customerBrowse);

//       Expect:
        assertThat(customerTable.firstItem()).isEqualTo(customer);
    }


    @Test
    void given_oneCustomerExists_when_editingCustomer_then_editCustomerEditorIsShown(Screens screens) {

//        Given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        CustomerBrowse customerBrowse = screenInteractions.open(CustomerBrowse.class);
        TableInteractions<Customer> customerTable = customerTable(customerBrowse);

//        And
        Customer firstCustomer = customerTable.firstItem();

//        When
        customerTable.edit(firstCustomer);

//       Then:
        CustomerEdit customerEdit = screenInteractions.findOpenScreen(CustomerEdit.class);

//        Expect
        assertThat(customerEdit.getEditedEntity()).isEqualTo(firstCustomer);
    }

    @NotNull
    private TableInteractions<Customer> customerTable(CustomerBrowse customerBrowse) {
        return TableInteractions.of(customerBrowse, Customer.class, "customersTable");
    }

    @AfterEach
    void tearDown() {
        dataManager.remove(customer);
    }
}