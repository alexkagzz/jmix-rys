package com.kagzz.jmix.rys.order.dto;

import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.order.entity.OrderLine;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderData {
    LocalDate orderDate;
    Customer customer;
    List<OrderLine> orderLines;
}
