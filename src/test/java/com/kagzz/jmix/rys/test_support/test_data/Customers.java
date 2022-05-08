package com.kagzz.jmix.rys.test_support.test_data;


import com.kagzz.jmix.rys.app.dto.AddressData;
import com.kagzz.jmix.rys.app.entity.Address;
import com.kagzz.jmix.rys.app.mapper.AddressMapper;
import com.kagzz.jmix.rys.customer.dto.CustomerData;
import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.customer.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("rys_Customers")
public class Customers implements TestDataProvisioning<CustomerData, CustomerData.CustomerDataBuilder, Customer> {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressMapper addressMapper;

    public static final String DEFAULT_ORDER_DATE = "first_name";
    public static final String DEFAULT_LAST_NAME = "last_name";
    public static final String DEFAULT_EMAIL = "first_name@last_name.com";
    public static final String DEFAULT_STREET = "street";
    public static final String DEFAULT_POST_CODE = "postcode";
    public static final String DEFAULT_CITY = "city";

    @Override
    public CustomerData.CustomerDataBuilder defaultData() {
        return CustomerData.builder()
                .firstName(DEFAULT_ORDER_DATE)
                .lastName(DEFAULT_LAST_NAME)
                .email(DEFAULT_EMAIL)
                .address(defaultAddress());
    }

    public Address defaultAddress() {
        return addressMapper.toEntity(AddressData.builder()
                .street(DEFAULT_STREET)
                .postCode(DEFAULT_POST_CODE)
                .city(DEFAULT_CITY)
                .build());
    }

    @Override
    public Customer save(CustomerData customerData)  {
        return customerRepository.save(customerData);
    }

    @Override
    public Customer saveDefault() {
        return save(defaultData().build());
    }

}