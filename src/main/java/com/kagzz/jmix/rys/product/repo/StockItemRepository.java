package com.kagzz.jmix.rys.product.repo;

 import com.kagzz.jmix.rys.product.dto.StockItemData;
 import com.kagzz.jmix.rys.product.entity.StockItem;
 import com.kagzz.jmix.rys.product.mapper.StockItemMapper;
 import com.kagzz.jmix.rys.app.repo.EntityRepository;
 import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_StockItemRepository")
public class StockItemRepository implements EntityRepository<StockItemData, StockItem> {

    @Autowired
    StockItemMapper mapper;

    @Autowired
    DataManager dataManager;

    @Override
    public StockItem save(StockItemData stockItemData) {
        return dataManager.save(mapper.toEntity(stockItemData));
    }
}