package com.kagzz.jmix.rys.product.screen.stockitem;

import io.jmix.ui.screen.*;
import com.kagzz.jmix.rys.product.entity.StockItem;

@UiController("rys_StockItem.browse")
@UiDescriptor("stock-item-browse.xml")
@LookupComponent("stockItemsTable")
public class StockItemBrowse extends StandardLookup<StockItem> {
}