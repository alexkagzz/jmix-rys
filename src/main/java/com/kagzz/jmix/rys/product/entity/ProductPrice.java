package com.kagzz.jmix.rys.product.entity;

import com.kagzz.jmix.rys.app.entity.Money;
import com.kagzz.jmix.rys.app.entity.StandardTenantEntity;
import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "RYS_PRODUCT_PRICE", indexes = {
        @Index(name = "IDX_PRODUCTPRICE_PRODUCT_ID", columnList = "PRODUCT_ID")
})
@Entity(name = "rys_ProductPrice")
public class ProductPrice extends StandardTenantEntity {

    @Valid
    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "PRICE_AMOUNT")),
            @AttributeOverride(name = "currency", column = @Column(name = "PRICE_CURRENCY"))
    })
    private Money price;

    @Column(name = "UNIT", nullable = false)
    @NotNull
    private String unit;

    @NotNull
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

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

    @InstanceName
    @DependsOnProperties({"unit", "price"})
    public String getInstanceName() {
        return String.format("%s / %s", price, unit);
    }
}