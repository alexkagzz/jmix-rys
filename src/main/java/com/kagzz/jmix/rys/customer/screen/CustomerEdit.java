package com.kagzz.jmix.rys.customer.screen;

import io.jmix.ui.screen.*;
import com.kagzz.jmix.rys.customer.entity.Customer;

@UiController("rys_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}