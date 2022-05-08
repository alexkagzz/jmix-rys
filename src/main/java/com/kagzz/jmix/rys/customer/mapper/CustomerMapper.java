package com.kagzz.jmix.rys.customer.mapper;


import com.kagzz.jmix.rys.app.JmixEntityFactory;
import com.kagzz.jmix.rys.customer.dto.CustomerData;
import com.kagzz.jmix.rys.customer.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JmixEntityFactory.class})
public interface CustomerMapper {

    Customer toEntity(CustomerData customerData);
}
