package com.kagzz.jmix.rys.product.mapper;

import com.kagzz.jmix.rys.app.JmixEntityFactory;
import com.kagzz.jmix.rys.product.dto.ProductCategoryData;
import com.kagzz.jmix.rys.product.entity.ProductCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface ProductCategoryMapper {

    ProductCategory toEntity(ProductCategoryData productCategoryData);
}
