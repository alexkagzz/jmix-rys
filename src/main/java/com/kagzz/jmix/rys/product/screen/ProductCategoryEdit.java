package com.kagzz.jmix.rys.product.screen;

import io.jmix.ui.screen.*;
import com.kagzz.jmix.rys.product.entity.ProductCategory;

@UiController("rys_ProductCategory.edit")
@UiDescriptor("product-category-edit.xml")
@EditedEntityContainer("productCategoryDc")
public class ProductCategoryEdit extends StandardEditor<ProductCategory> {
}