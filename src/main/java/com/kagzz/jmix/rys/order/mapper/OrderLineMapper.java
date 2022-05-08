package com.kagzz.jmix.rys.order.mapper;

import com.kagzz.jmix.rys.app.JmixEntityFactory;
import com.kagzz.jmix.rys.order.dto.OrderLineData;
import com.kagzz.jmix.rys.order.entity.OrderLine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface OrderLineMapper {

    OrderLine toEntity(OrderLineData orderLineData);
}
