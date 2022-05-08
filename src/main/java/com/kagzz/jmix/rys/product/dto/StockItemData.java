package com.kagzz.jmix.rys.product.dto;

import com.kagzz.jmix.rys.product.entity.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StockItemData {
    String identifier;
    Product product;
}
