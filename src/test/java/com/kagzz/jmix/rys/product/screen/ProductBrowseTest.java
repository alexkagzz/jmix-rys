package com.kagzz.jmix.rys.product.screen;

import com.kagzz.jmix.rys.product.entity.Product;
import com.kagzz.jmix.rys.test_support.DatabaseCleanup;
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


class ProductBrowseTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    private Product product;

    @BeforeEach
    void setUp() {
        createInitialProduct();
    }

    private void createInitialProduct() {
        product = dataManager.create(Product.class);
        product.setName("Foo Product");
        product.setDescription("Description for prod One");
        dataManager.save(product);
    }

    @Test
    void given_oneProductExists_when_openProductBrowse_then_tableContainsTheProduct(Screens screens) {

//        Given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        ProductBrowse productBrowse = screenInteractions.open(ProductBrowse.class);
        TableInteractions<Product> productTable = productTable(productBrowse);

//       Expect:
        assertThat(productTable.firstItem()).isEqualTo(product);
    }


    @Test
    void given_oneProductExists_when_editingProduct_then_editProductEditorIsShown(Screens screens) {

//        Given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        ProductBrowse productBrowse = screenInteractions.open(ProductBrowse.class);
        TableInteractions<Product> productTable = productTable(productBrowse);

//        And
        Product firstProduct = productTable.firstItem();

//        When
        productTable.edit(firstProduct);

//       Then:
        ProductEdit productEdit = screenInteractions.findOpenScreen(ProductEdit.class);

//        Expect
        assertThat(productEdit.getEditedEntity()).isEqualTo(firstProduct);
    }

    @NotNull
    private TableInteractions<Product> productTable(ProductBrowse productBrowse) {
        return TableInteractions.of(productBrowse, Product.class, "productsTable");
    }

    @AfterEach
    void tearDown() {
        dataManager.remove(product);
    }
}