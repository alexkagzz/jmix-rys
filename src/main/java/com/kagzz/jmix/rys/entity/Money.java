package com.kagzz.jmix.rys.entity;

import io.jmix.core.Messages;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@JmixEntity(name = "rys_Money")
@Embeddable
public class Money {
    @PositiveOrZero
    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    @NotNull
    private BigDecimal amount;

    @Column(name = "CURRENCY", nullable = false)
    @NotNull
    private String currency;

    public Currency getCurrency() {
        return currency == null ? null : Currency.fromId(currency);
    }

    public void setCurrency(Currency currency) {
        this.currency = currency == null ? null : currency.getId();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @InstanceName
    @DependsOnProperties({"amount", "currency"})
    public String getInstanceName(Messages messages) {
        return String.format("%s %s", amount, messages.getMessage(getCurrency()));
    }
}