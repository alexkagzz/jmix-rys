package com.kagzz.jmix.rys.product.entity;

import com.kagzz.jmix.rys.entity.StandardEntity;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@JmixEntity
@Table(name = "RYS_PRODUCT_PRICE", indexes = {
        @Index(name = "IDX_PRODUCTPRICE_PRODUCT_ID", columnList = "PRODUCT_ID")
})
@Entity(name = "rys_ProductPrice")
public class ProductPrice extends StandardEntity {
    @PositiveOrZero
    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    @NotNull
    private BigDecimal amount;

    @Column(name = "UNIT", nullable = false)
    @NotNull
    private String unit;

    @NotNull
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PriceUnit getUnit() {
        return unit == null ? null : PriceUnit.fromId(unit);
    }

    public void setUnit(PriceUnit unit) {
        this.unit = unit == null ? null : unit.getId();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @InstanceName
    @DependsOnProperties({"unit", "amount"})
    public String getInstanceName() {
        return String.format("%s / %s", amount, unit);
    }
}