package com.kagzz.jmix.rys.product.repo;


import com.kagzz.jmix.rys.product.dto.ProductCategoryData;
import com.kagzz.jmix.rys.product.entity.ProductCategory;
import com.kagzz.jmix.rys.product.mapper.ProductCategoryMapper;
import com.kagzz.jmix.rys.app.repo.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_ProductCategoryRepository")
public class ProductCategoryRepository implements EntityRepository<ProductCategoryData, ProductCategory> {

    @Autowired
    DataManager dataManager;

    @Autowired
    ProductCategoryMapper mapper;

    @Override
    public ProductCategory save(ProductCategoryData productCategoryData) {
        return dataManager.save(mapper.toEntity(productCategoryData));
    }

}
