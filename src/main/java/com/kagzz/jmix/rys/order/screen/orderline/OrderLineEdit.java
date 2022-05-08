package com.kagzz.jmix.rys.order.screen.orderline;

import io.jmix.ui.screen.*;
import com.kagzz.jmix.rys.order.entity.OrderLine;

@UiController("rys_OrderLine.edit")
@UiDescriptor("order-line-edit.xml")
@EditedEntityContainer("orderLineDc")
public class OrderLineEdit extends StandardEditor<OrderLine> {
}