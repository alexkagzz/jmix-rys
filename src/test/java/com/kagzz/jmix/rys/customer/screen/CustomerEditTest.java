package com.kagzz.jmix.rys.customer.screen;

import com.kagzz.jmix.rys.RentYourStuffApplication;
import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.test_support.DatabaseCleanup;
import com.kagzz.jmix.rys.test_support.ui.FormInteractions;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.UiTestAssistConfiguration;
import io.jmix.ui.testassist.junit.UiTest;
import io.jmix.ui.util.OperationResult;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@UiTest(authenticatedUser = "admin", mainScreenId = "rys_MainScreen", screenBasePackages = "com.kagzz.jmix.rys")
@ContextConfiguration(classes = {RentYourStuffApplication.class, UiTestAssistConfiguration.class})
@AutoConfigureTestDatabase
class CustomerEditTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CustomerEditTest.class);
    @Autowired
    DataManager dataManager;
    @Autowired
    DatabaseCleanup databaseCleanup;

    FormInteractions formInteractions;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(Customer.class);
    }

    @Test
    void given_validCustomer_when_saveCustomerThroughTheForm_then_customerIsSaved(Screens screens) {

//        Given:
        CustomerEdit customerEdit = openCustomerBrowse(screens);
        formInteractions = FormInteractions.of(customerEdit);


//       And
        String firstName = "Foo"+ UUID.randomUUID();
        String lastName = "Bar"+ UUID.randomUUID();
        log.info("Customer FirstName is {}", firstName);
        log.info("Customer LastName is {}", lastName);
        formInteractions.setFieldValue("firstNameField",  firstName);
        formInteractions.setFieldValue("lastNameField", lastName);
        formInteractions.setFieldValue("emailField",  "foo@bar.com");
        formInteractions.setFieldValue("addressStreetField",  "Foo Street");
        formInteractions.setFieldValue("addressPostalCodeField",  "00300");
        formInteractions.setFieldValue("addressCityField",  "Kiambu");

//        When
        OperationResult operationResult = formInteractions.saveForm();

//        Then:
        assertThat(operationResult)
                .isEqualTo(OperationResult.success());

        Optional<Customer> savedCustomer = getCustomerByAttribute("firstName", firstName);

        assertThat(savedCustomer)
                .isPresent()
                .get()
                .extracting("lastName")
                .isEqualTo(lastName);


    }

    @Test
    void given_withoutStreet_when_saveCustomerThroughTheForm_then_customerIsNotSaved(Screens screens) {

//        Given:
        CustomerEdit customerEdit = openCustomerBrowse(screens);
        formInteractions = FormInteractions.of(customerEdit);

//       And
        String firstName = "Foo"+ UUID.randomUUID();
        String lastName = "Bar"+ UUID.randomUUID();
        log.info("Customer FirstName is {}", firstName);
        log.info("Customer LastName is {}", lastName);
        formInteractions.setFieldValue("firstNameField",  firstName);
        formInteractions.setFieldValue("lastNameField",  lastName);
        formInteractions.setFieldValue("emailField",  "foo@bar.com");
        formInteractions.setFieldValue("addressPostalCodeField",  "00300");
        formInteractions.setFieldValue("addressCityField",  "Kiambu");

//        When
        OperationResult operationResult = formInteractions.saveForm();

//        Then:
        assertThat(operationResult).isEqualTo(OperationResult.fail());

        Optional<Customer> savedCustomer = getCustomerByAttribute("firstName", firstName);

        assertThat(savedCustomer)
                .isNotPresent();

    }

    @NotNull
    private Optional<Customer> getCustomerByAttribute( String attribute, String value) {
        return dataManager.load(Customer.class)
                .condition(PropertyCondition.equal(attribute, value))
                .optional();
    }

    @NotNull
    private CustomerEdit openCustomerBrowse(Screens screens) {
        CustomerEdit customerEdit = screens.create(CustomerEdit.class);
        Customer customer = dataManager.create(Customer.class);
        customerEdit.setEntityToEdit(customer);
        customerEdit.show();
        return customerEdit;
    }
}