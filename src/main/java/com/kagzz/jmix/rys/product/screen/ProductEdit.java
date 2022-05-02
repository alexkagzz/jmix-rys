package com.kagzz.jmix.rys.product.screen;

import io.jmix.ui.screen.*;
import com.kagzz.jmix.rys.product.entity.Product;

@UiController("rys_Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
public class ProductEdit extends StandardEditor<Product> {
}