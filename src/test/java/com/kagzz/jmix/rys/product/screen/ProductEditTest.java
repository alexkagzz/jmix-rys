package com.kagzz.jmix.rys.product.screen;

import com.kagzz.jmix.rys.product.entity.PriceUnit;
import com.kagzz.jmix.rys.product.entity.Product;
import com.kagzz.jmix.rys.product.entity.ProductPrice;
import com.kagzz.jmix.rys.test_support.DatabaseCleanup;
import com.kagzz.jmix.rys.test_support.ui.FormInteractions;
import com.kagzz.jmix.rys.test_support.ui.ScreenInteractions;
import com.kagzz.jmix.rys.test_support.ui.TableInteractions;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductEditTest extends WebIntegrationTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ProductEditTest.class);
    @Autowired
    DataManager dataManager;
    @Autowired
    DatabaseCleanup databaseCleanup;

    FormInteractions formInteractions;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(Product.class);
    }

    @Test
    void given_validProduct_when_saveProductThroughTheForm_then_productIsSaved(Screens screens) {

//        Given:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductEdit  productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
        formInteractions = FormInteractions.of(productEdit);


//       And
        String name = "Foo "+ UUID.randomUUID();
        String description = "Bar "+ UUID.randomUUID();
        log.info("Product Name is {}", name);
        log.info("Product Description is {}", description);
        formInteractions.setTextFieldValue("nameField",  name);
        formInteractions.setTextAreaValue("descriptionField", description);

//        When
        OperationResult operationResult = formInteractions.saveForm();

//        Then:
        assertThat(operationResult)
                .isEqualTo(OperationResult.success());

        Optional<Product> savedProduct = getProductByAttribute("name", name);

        assertThat(savedProduct)
                .isPresent()
                .get()
                .extracting("description")
                .isEqualTo(description);


    }

//    @Disabled
    @Test
    void given_productWithoutName_when_saveProductThroughTheForm_then_productIsNotSaved(Screens screens) {

//        Given:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductEdit  productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
        formInteractions = FormInteractions.of(productEdit);

//       And
        String description = UUID.randomUUID().toString();
        formInteractions.setTextFieldValue("nameField",  null);
        formInteractions.setTextAreaValue("descriptionField", description);

//        When
        OperationResult operationResult = formInteractions.saveForm();

//        Then:
        assertThat(operationResult).isEqualTo(OperationResult.fail());

        Optional<Product> savedProduct = getProductByAttribute("description", description);

        assertThat(savedProduct).isNotPresent();

        assertThat(dataManager.load(Product.class).all().list()).isEmpty();

    }

    @Test
    void given_validProducWithPrice_when_saveProductThroughTheForm_then_productAndPriceAreSaved(Screens screens) {

//        Given:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductEdit  productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
        formInteractions = FormInteractions.of(productEdit);

//       And
        String name = "Foo Product"+ UUID.randomUUID();
        String description = "Bar "+ UUID.randomUUID();
        log.info("Product Name is {}", name);
        log.info("Product Description is {}", description);
        formInteractions.setTextFieldValue("nameField",  name);
        formInteractions.setTextAreaValue("descriptionField", description);

//        And
        TableInteractions<ProductPrice> pricesTable = TableInteractions.of(productEdit, ProductPrice.class, "pricesTable");
        pricesTable.create();
        ProductPriceEdit productPriceEdit = screenInteractions.findOpenScreen(ProductPriceEdit.class);
        FormInteractions priceForm = FormInteractions.of(productPriceEdit);

        BigDecimal amount = BigDecimal.valueOf(5);
        priceForm.setNumberFieldValue("amountField", amount);
        PriceUnit unit = PriceUnit.DAY;
        priceForm.setEnumFieldValue("unitField", unit);


//        When
        OperationResult saveProductPriceOperationResult = priceForm.saveForm();
        OperationResult productFormResult = formInteractions.saveForm();

//        Then:
        assertThat(saveProductPriceOperationResult).isEqualTo(OperationResult.success());
        assertThat(productFormResult).isEqualTo(OperationResult.success());

        Optional<Product> savedProduct = getProductByAttribute("name", name);

        assertThat(savedProduct)
                .isPresent()
                .get()
                .extracting("description")
                .isEqualTo(description);

        assertThat(getPrices(savedProduct)).hasSize(1);

        ProductPrice price = getPrices(savedProduct).get(0);

        assertThat(price.getAmount()).isEqualByComparingTo(amount);

        assertThat(price.getUnit()).isEqualTo(unit);
    }

    private List<ProductPrice> getPrices(Optional<Product> savedProduct) {
        return savedProduct.get().getPrices();
    }

    @NotNull
    private Optional<Product> getProductByAttribute( String attribute, String value) {
        return dataManager.load(Product.class)
                .condition(PropertyCondition.equal(attribute, value))
                .optional();
    }
}