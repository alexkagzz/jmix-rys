package com.kagzz.jmix.rys.customer.repo;

import com.kagzz.jmix.rys.customer.dto.CustomerData;
import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.customer.mapper.CustomerMapper;
import com.kagzz.jmix.rys.app.repo.EntityRepository;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rys_CustomerRepository")
public class CustomerRepository implements EntityRepository<CustomerData, Customer> {

    @Autowired
    DataManager dataManager;

    @Autowired
    CustomerMapper mapper;

    @Override
    public Customer save(CustomerData customerData) {
        return dataManager.save(mapper.toEntity(customerData));
    }

}