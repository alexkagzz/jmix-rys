package com.kagzz.jmix.rys.order.screen;

import io.jmix.ui.screen.*;
import com.kagzz.jmix.rys.order.entity.Order;

@UiController("rys_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}