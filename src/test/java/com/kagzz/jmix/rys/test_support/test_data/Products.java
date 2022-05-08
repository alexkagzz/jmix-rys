package com.kagzz.jmix.rys.test_support.test_data;

import com.kagzz.jmix.rys.product.dto.ProductData;
import com.kagzz.jmix.rys.product.entity.Product;
import com.kagzz.jmix.rys.product.mapper.ProductMapper;
import com.kagzz.jmix.rys.product.repo.ProductRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_Products")
public class Products implements TestDataProvisioning<ProductData, ProductData.ProductDataBuilder, Product> {

    @Autowired
    ProductRepository productRepository;

    public static String DEFAULT_NAME = "product_name";
    public static String DEFAULT_DESCRIPTION = "product_description";

    @Override
    public ProductData.ProductDataBuilder defaultData() {
        return ProductData.builder()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .category(null);
    }

    @Override
    public Product save(ProductData productData)  {
        return productRepository.save(productData);
    }

    @Override
    public Product saveDefault() {
        return save(defaultData().build());
    }

}