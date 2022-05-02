package com.kagzz.jmix.rys.product.screen;

import io.jmix.ui.screen.*;
import com.kagzz.jmix.rys.product.entity.ProductCategory;

@UiController("rys_ProductCategory.browse")
@UiDescriptor("product-category-browse.xml")
@LookupComponent("productCategoriesTable")
public class ProductCategoryBrowse extends StandardLookup<ProductCategory> {
}