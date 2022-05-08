package com.kagzz.jmix.rys.customer.dto;

import com.kagzz.jmix.rys.app.entity.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerData {
    String firstName;
    String lastName;
    String email;
    Address address;
}
