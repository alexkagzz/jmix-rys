package com.kagzz.jmix.rys.customer.screen;

import com.kagzz.jmix.rys.RentYourStuffApplication;
import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.entity.Address;
import com.kagzz.jmix.rys.test_support.DatabaseCleanup;
import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.Screen;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@UiTest(authenticatedUser = "admin", mainScreenId = "rys_MainScreen", screenBasePackages = "com.kagzz.jmix.rys")
@ContextConfiguration(classes = {RentYourStuffApplication.class, UiTestAssistConfiguration.class})
@AutoConfigureTestDatabase
class CustomerBrowseTest {
    @Autowired
    DataManager dataManager;

    @Autowired
    DatabaseCleanup databaseCleanup;

    private Customer customer;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(Customer.class);

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

    @Test
    void given_oneCustomerExists_when_editingCustomer_then_editCustomerEditorIsShown(Screens screens) {

//        Given:
        CustomerBrowse customerBrowse = openCustomerBrowse(screens);

//        And
        Customer firstCustomer = getFirstLoadedCustomer(customerBrowse);

//        And
        selectCustomerInTable(customerBrowse, firstCustomer);

//        When
        getButton(customerBrowse, "editBtn").click();

//       Then:
        CustomerEdit customerEdit = getScreenOfType(screens, CustomerEdit.class);

//        Expect
        assertThat(customerEdit.getEditedEntity()).isEqualTo(firstCustomer);
    }

    private void selectCustomerInTable(CustomerBrowse customerBrowse, Customer firstCustomer) {
        Table<Customer> customersTable = getCustomersTable(customerBrowse);
        assert customersTable != null;
        customersTable.setSelected(firstCustomer);
    }

    @NotNull
    private <T> T getScreenOfType(Screens screens, Class<T> tClass) {
        Screen screen = screens.getOpenedScreens().getActiveScreens().stream().findFirst().orElseThrow();
        assertThat(screen).isInstanceOf(tClass);
        return (T) screen;
    }

    @Nullable
    private Button getButton(CustomerBrowse customerBrowse, String buttonId) {
        return (Button) customerBrowse.getWindow().getComponent(buttonId);
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