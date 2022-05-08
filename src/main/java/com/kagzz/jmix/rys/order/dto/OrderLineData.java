package com.kagzz.jmix.rys.order.dto;

import com.kagzz.jmix.rys.order.entity.Order;
import com.kagzz.jmix.rys.product.entity.StockItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderLineData {
    StockItem stockItem;
    Order order;
    LocalDateTime startsAt;
    LocalDateTime endsAt;
}
