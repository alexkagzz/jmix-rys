package com.kagzz.jmix.rys.order.repo;

import com.kagzz.jmix.rys.order.dto.OrderData;
import com.kagzz.jmix.rys.order.entity.Order;
import com.kagzz.jmix.rys.order.mapper.OrderMapper;
import com.kagzz.jmix.rys.app.repo.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_OrderRepository")
public class OrderRepository implements EntityRepository<OrderData, Order> {

    @Autowired
    DataManager dataManager;

    @Autowired
    OrderMapper mapper;

    @Override
    public Order save(OrderData orderData) {
        return dataManager.save(mapper.toEntity(orderData));
    }

}