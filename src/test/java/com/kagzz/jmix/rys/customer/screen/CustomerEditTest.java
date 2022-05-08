package com.kagzz.jmix.rys.customer.screen;

import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.test_support.DatabaseCleanup;
import com.kagzz.jmix.rys.test_support.ui.FormInteractions;
import com.kagzz.jmix.rys.test_support.ui.ScreenInteractions;
import com.kagzz.jmix.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Screens;
import io.jmix.ui.util.OperationResult;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerEditTest extends WebIntegrationTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CustomerEditTest.class);
    @Autowired
    DataManager dataManager;
    FormInteractions formInteractions;

    @Test
    void given_validCustomer_when_saveCustomerThroughTheForm_then_customerIsSaved(Screens screens) {

//        Given:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        CustomerEdit customerEdit = screenInteractions.openEditorForCreation(CustomerEdit.class, Customer.class);
        formInteractions = FormInteractions.of(customerEdit);


//       And
        String firstName = "Foo"+ UUID.randomUUID();
        String lastName = "Bar"+ UUID.randomUUID();
        log.info("Customer FirstName is {}", firstName);
        log.info("Customer LastName is {}", lastName);
        formInteractions.setTextFieldValue("firstNameField",  firstName);
        formInteractions.setTextFieldValue("lastNameField", lastName);
        formInteractions.setTextFieldValue("emailField",  "foo@bar.com");
        formInteractions.setTextFieldValue("addressStreetField",  "Foo Street");
        formInteractions.setTextFieldValue("addressPostalCodeField",  "00300");
        formInteractions.setTextFieldValue("addressCityField",  "Kiambu");

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
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        CustomerEdit customerEdit = screenInteractions.openEditorForCreation(CustomerEdit.class, Customer.class);
        formInteractions = FormInteractions.of(customerEdit);

//       And
        String firstName = "Foo"+ UUID.randomUUID();
        String lastName = "Bar"+ UUID.randomUUID();
        log.info("Customer FirstName is {}", firstName);
        log.info("Customer LastName is {}", lastName);
        formInteractions.setTextFieldValue("firstNameField",  firstName);
        formInteractions.setTextFieldValue("lastNameField",  lastName);
        formInteractions.setTextFieldValue("emailField",  "foo@bar.com");
        formInteractions.setTextFieldValue("addressPostalCodeField",  "00300");
        formInteractions.setTextFieldValue("addressCityField",  "Kiambu");

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
}