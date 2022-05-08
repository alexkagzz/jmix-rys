package com.kagzz.jmix.rys.product.mapper;

import com.kagzz.jmix.rys.app.JmixEntityFactory;
import com.kagzz.jmix.rys.product.dto.StockItemData;
import com.kagzz.jmix.rys.product.entity.StockItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface StockItemMapper {

    StockItem toEntity(StockItemData stockItemData);
}
