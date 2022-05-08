package com.kagzz.jmix.rys.product.repo;


import com.kagzz.jmix.rys.product.dto.ProductData;
import com.kagzz.jmix.rys.product.entity.Product;
import com.kagzz.jmix.rys.product.mapper.ProductMapper;
import com.kagzz.jmix.rys.app.repo.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_ProductRepository")
public class ProductRepository implements EntityRepository<ProductData, Product> {

    @Autowired
    ProductMapper mapper;

    @Autowired
    DataManager dataManager;

    @Override
    public Product save(ProductData productData) {
        return dataManager.save(mapper.toEntity(productData));
    }
}
