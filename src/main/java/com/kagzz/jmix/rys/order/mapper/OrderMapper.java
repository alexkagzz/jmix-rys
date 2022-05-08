package com.kagzz.jmix.rys.order.mapper;

import com.kagzz.jmix.rys.app.JmixEntityFactory;
import com.kagzz.jmix.rys.order.dto.OrderData;
import com.kagzz.jmix.rys.order.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface OrderMapper {

    Order toEntity(OrderData orderData);
}
