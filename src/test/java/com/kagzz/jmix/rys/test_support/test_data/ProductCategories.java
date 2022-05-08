package com.kagzz.jmix.rys.test_support.test_data;

import com.kagzz.jmix.rys.product.dto.ProductCategoryData;
import com.kagzz.jmix.rys.product.entity.ProductCategory;
import com.kagzz.jmix.rys.product.repo.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_ProductCategories")
public class ProductCategories
        implements TestDataProvisioning<ProductCategoryData, ProductCategoryData.ProductCategoryDataBuilder, ProductCategory> {

    @Autowired
    ProductCategoryRepository repository;

    public static String DEFAULT_NAME = "product_category_name";

    @Override
    public ProductCategoryData.ProductCategoryDataBuilder defaultData() {
        return ProductCategoryData.builder()
                .name(DEFAULT_NAME);
    }

    @Override
    public ProductCategory save(ProductCategoryData productCategoryData) {
        return repository.save(productCategoryData);
    }

    @Override
    public ProductCategory saveDefault() {
        return save(defaultData().build());
    }
}