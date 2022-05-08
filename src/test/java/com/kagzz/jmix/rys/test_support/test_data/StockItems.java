package com.kagzz.jmix.rys.test_support.test_data;

import com.kagzz.jmix.rys.product.dto.StockItemData;
import com.kagzz.jmix.rys.product.entity.StockItem;
import com.kagzz.jmix.rys.product.repo.StockItemRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_StockItems")
public class StockItems
        implements TestDataProvisioning<StockItemData, StockItemData.StockItemDataBuilder, StockItem> {

    @Autowired
    DataManager dataManager;

    @Autowired
    StockItemRepository repository;

    public static String DEFAULT_IDENTIFIER = "stock_item_identifier";

    @Override
    public StockItemData.StockItemDataBuilder defaultData() {
        return StockItemData.builder()
                .identifier(DEFAULT_IDENTIFIER)
                .product(null);
    }

    @Override
    public StockItem save(StockItemData stockItemData) {
        return repository.save(stockItemData);
    }

    @Override
    public StockItem saveDefault() {
        return save(defaultData().build());
    }

}