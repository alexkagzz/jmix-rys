package com.kagzz.jmix.rys.product.screen;

import com.kagzz.jmix.rys.app.RentYourStuffProperties;
import com.kagzz.jmix.rys.app.entity.Money;
import io.jmix.core.Messages;
import io.jmix.ui.component.CurrencyField;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import com.kagzz.jmix.rys.product.entity.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@UiController("rys_ProductPrice.edit")
@UiDescriptor("product-price-edit.xml")
@EditedEntityContainer("productPriceDc")
public class ProductPriceEdit extends StandardEditor<ProductPrice> {

    @Autowired
    private CurrencyField<BigDecimal> priceAmountField;
    @Autowired
    private DataContext dataContext;

    @Autowired
    private RentYourStuffProperties rysProperties;

    @Autowired
    private Messages messages;

    @Subscribe
    public void onInitEntity(InitEntityEvent<ProductPrice> event) {
        Money kshPrice = dataContext.create(Money.class);
        kshPrice.setCurrency(rysProperties.getCurrency());
        event.getEntity().setPrice(kshPrice);
    }


    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        priceAmountField.setCurrency(messages.getMessage(getEditedEntity().getPrice().getCurrency()));
    }
    
    
}