package com.kagzz.jmix.rys.app.dto;

import com.kagzz.jmix.rys.app.entity.Currency;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyData {
    BigDecimal amount;
    Currency currency;
}
