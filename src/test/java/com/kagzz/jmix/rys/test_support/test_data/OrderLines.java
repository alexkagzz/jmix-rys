package com.kagzz.jmix.rys.test_support.test_data;

import com.kagzz.jmix.rys.order.dto.OrderLineData;
import com.kagzz.jmix.rys.order.entity.OrderLine;
import com.kagzz.jmix.rys.order.repo.OrderLineRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.kagzz.jmix.rys.test_support.test_data.Orders.*;


@Component("rys_OrderLines")
public class OrderLines
        implements TestDataProvisioning<OrderLineData, OrderLineData.OrderLineDataBuilder, OrderLine> {

    @Autowired
    DataManager dataManager;

    @Autowired
    OrderLineRepository orderLineRepository;
    public static final LocalDateTime DEFAULT_STARTS_AT = LocalDateTime.of(DEFAULT_ORDER_DATE, LocalTime.of(8, 0));
    public static final LocalDateTime DEFAULT_ENDS_AT = LocalDateTime.of(DEFAULT_ORDER_DATE, LocalTime.of(16, 0));


    @Override
    public OrderLineData.OrderLineDataBuilder defaultData() {
        return OrderLineData.builder()
                .startsAt(DEFAULT_STARTS_AT)
                .endsAt(DEFAULT_ENDS_AT);
    }

    @Override
    public OrderLine save(OrderLineData orderLineData) {
        return orderLineRepository.save(orderLineData);
    }

    @Override
    public OrderLine saveDefault() {
        return save(defaultData().build());
    }

}