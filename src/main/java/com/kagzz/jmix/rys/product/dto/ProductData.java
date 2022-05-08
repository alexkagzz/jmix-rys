package com.kagzz.jmix.rys.product.dto;

import com.kagzz.jmix.rys.product.entity.ProductCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductData {
    String name;
    String description;
    ProductCategory category;

}