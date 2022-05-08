package com.kagzz.jmix.rys.product.screen;

import com.kagzz.jmix.rys.product.entity.*;
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
import org.junit.jupiter.api.Nested;
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
    FormInteractions formInteractions;
    ProductEdit productEdit;
    ScreenInteractions screenInteractions;

    @Nested
    class withOpenedProductEditForm{
        @BeforeEach
        void setUp(Screens screens) {
            initProductEditForm(screens);
        }    @Test
        void given_validProduct_when_saveProductThroughTheForm_then_productIsSaved() {

//        Given:
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
        void given_productWithoutName_when_saveProductThroughTheForm_then_productIsNotSaved() {

//        Given:
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
        void given_validProducWithPrice_when_saveProductThroughTheForm_then_productAndPriceAreSaved() {

//        Given:
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
            priceForm.setCurrencyFieldValue("priceAmountField", amount);
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

            assertThat(price.getPrice().getAmount()).isEqualByComparingTo(amount);

            assertThat(price.getUnit()).isEqualTo(unit);
        }

        @Test
        void given_validProductWithStockItem_when_saveProductThroughTheForm_then_productAndStockItemAreSaved() {

//        Given:
            String name = "Foo Product"+ UUID.randomUUID();
            String description = "Bar "+ UUID.randomUUID();
            log.info("Product Name is {}", name);
            log.info("Product Description is {}", description);
            formInteractions.setTextFieldValue("nameField",  name);
            formInteractions.setTextAreaValue("descriptionField", description);

//        And
            TableInteractions<StockItem> stockItemsTable = TableInteractions.of(productEdit, StockItem.class, "stockItemsTable");
            stockItemsTable.create();
            StockItemEdit stockItemEdit = screenInteractions.findOpenScreen(StockItemEdit.class);
            FormInteractions stockItemForm = FormInteractions.of(stockItemEdit);

            String expectedvalue =  "Foo Expected Value "+ UUID.randomUUID();;
            stockItemForm.setTextFieldValue("identifierField", expectedvalue);


//        When
            OperationResult stockItemFormResult = stockItemForm.saveForm();
            OperationResult productFormResult = formInteractions.saveForm();

//        Then:
            assertThat(stockItemFormResult).isEqualTo(OperationResult.success());
            assertThat(productFormResult).isEqualTo(OperationResult.success());

            Optional<Product> savedProduct = getProductByAttribute("name", name);

            assertThat(savedProduct)
                    .isPresent()
                    .get()
                    .extracting("description")
                    .isEqualTo(description);

            List<StockItem> stockItems = savedProduct.get().getStockItems();

            assertThat(stockItems).hasSize(1);

            StockItem stockItem = stockItems.get(0);

            assertThat(stockItem.getIdentifier()).isEqualTo(expectedvalue);
        }
    }

    @Nested
    class productCategoryTests{

        private ProductCategory productCategory1;
        private ProductCategory productCategory2;
        private ProductCategory productCategory;

        @BeforeEach
        void setUp(Screens screens) {
            productCategory = saveProductCategory("Foo Product category");
            productCategory1 = saveProductCategory("Foo Product category 1");
            productCategory2 = saveProductCategory("Foo Product category 2");
            initProductEditForm(screens);
        }
        @Test
        void given_twoProductCategoriesAreInDB_when_openingTheProductEditor_then_categoriesAreDisplayedInTheComboBox() {

//       Expect:
            List<ProductCategory> availableProductCategories = formInteractions
                    .getEntityComboBoxValues("categoryField", ProductCategory.class);
            assertThat(availableProductCategories).contains(productCategory1, productCategory2);

//        assertThat(availableProductCategories.stream().map(ProductCategory::getName))
//                .contains(productCategory1.getName(), productCategory2.getName());
        }

        @Test
        void given_validProducWithCategory_when_saveProductThroughTheForm_then_productAndCategoryAssociationAreSaved(Screens screens) {

//       Given
            String name = "Foo Product";
            String description = "Bar ";
            log.info("Product Name is {}", name);
            log.info("Product Description is {}", description);
            formInteractions.setTextFieldValue("nameField",  name);
            formInteractions.setTextAreaValue("descriptionField", description);

//        And
            formInteractions.setEntityComboBoxFieldValue("categoryField", productCategory, ProductCategory.class);


//        When
            OperationResult productFormResult = formInteractions.saveForm();

//        Then:
            assertThat(productFormResult).isEqualTo(OperationResult.success());

            Optional<Product> savedProduct = getProductByAttribute("name", name);

            assertThat(savedProduct)
                    .isPresent()
                    .get()
                    .extracting("category")
                    .isEqualTo(productCategory);
        }
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

    @NotNull
    private ProductCategory saveProductCategory(String name) {
        ProductCategory productCategory = dataManager.create(ProductCategory.class);
        productCategory.setName(name);
        return  dataManager.save(productCategory);
    }

    private void initProductEditForm(Screens screens) {
        screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
        formInteractions = FormInteractions.of(productEdit);
    }
}