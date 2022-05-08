package com.kagzz.jmix.rys.order.repo;

import com.kagzz.jmix.rys.order.dto.OrderLineData;
import com.kagzz.jmix.rys.order.entity.OrderLine;
import com.kagzz.jmix.rys.order.mapper.OrderLineMapper;
import com.kagzz.jmix.rys.app.repo.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_OrderLineRepository")
public class OrderLineRepository implements EntityRepository<OrderLineData, OrderLine> {

    @Autowired
    DataManager dataManager;

    @Autowired
    OrderLineMapper mapper;

    @Override
    public OrderLine save(OrderLineData orderLineData) {
        return dataManager.save(mapper.toEntity(orderLineData));
    }

}