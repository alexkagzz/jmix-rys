package com.kagzz.jmix.rys.product.screen;

import io.jmix.ui.screen.*;
import com.kagzz.jmix.rys.product.entity.StockItem;

@UiController("rys_StockItem.edit")
@UiDescriptor("stock-item-edit.xml")
@EditedEntityContainer("stockItemDc")
public class StockItemEdit extends StandardEditor<StockItem> {
}