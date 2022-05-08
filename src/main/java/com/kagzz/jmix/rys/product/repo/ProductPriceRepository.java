package com.kagzz.jmix.rys.product.repo;


import com.kagzz.jmix.rys.product.dto.ProductPriceData;
import com.kagzz.jmix.rys.product.entity.ProductPrice;
import com.kagzz.jmix.rys.product.mapper.ProductPriceMapper;
import com.kagzz.jmix.rys.app.repo.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_ProductPriceRepository")
public class ProductPriceRepository implements EntityRepository<ProductPriceData, ProductPrice> {

    @Autowired
    ProductPriceMapper mapper;

    @Autowired
    DataManager dataManager;

    public ProductPrice save(ProductPriceData productData) {
        return dataManager.save(mapper.toEntity(productData));
    }
}