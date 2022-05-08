package com.kagzz.jmix.rys.order.screen;

import io.jmix.ui.screen.*;
import com.kagzz.jmix.rys.order.entity.Order;

@UiController("rys_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {
}