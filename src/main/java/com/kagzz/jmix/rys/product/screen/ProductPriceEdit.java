package com.kagzz.jmix.rys.product.screen;

import io.jmix.ui.screen.*;
import com.kagzz.jmix.rys.product.entity.ProductPrice;

@UiController("rys_ProductPrice.edit")
@UiDescriptor("product-price-edit.xml")
@EditedEntityContainer("productPriceDc")
public class ProductPriceEdit extends StandardEditor<ProductPrice> {
}