package com.kagzz.jmix.rys.customer.screen;

import io.jmix.ui.screen.*;
import com.kagzz.jmix.rys.customer.entity.Customer;

@UiController("rys_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}

