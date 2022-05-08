package com.kagzz.jmix.rys.order;

import com.kagzz.jmix.rys.customer.entity.Customer;
import com.kagzz.jmix.rys.order.entity.Order;
import com.kagzz.jmix.rys.order.entity.OrderLine;
import com.kagzz.jmix.rys.product.entity.Product;
import com.kagzz.jmix.rys.product.entity.StockItem;
import com.kagzz.jmix.rys.test_support.TenantUserEnvironment;
import com.kagzz.jmix.rys.test_support.test_data.*;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.kagzz.jmix.rys.order.entity.OrderAssert.assertThat;

@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
public class OrderStorageTest {

    private final LocalDate TODAY = LocalDate.now();
    private final LocalDateTime NOW = LocalDateTime.now();
    private final LocalDateTime IN_TWO_DAYS = NOW.plusDays(2);
    private final LocalDateTime IN_THREE_DAYS = NOW.plusDays(3);

    @Autowired
    Customers customers;
    @Autowired
    Products products;

    @Autowired
    DataManager dataManager;
    @Autowired
    StockItems stockItems;

    @Autowired
    Orders orders;

    @Autowired
    OrderLines orderLines;


    @Test
    void given_validOrder_when_storingOrderLines_then_allEntitiesAreStored() {

//        Given
        Customer customer = customers.saveDefault();

//        And
        Product product = products.save(
                products.defaultData()
                        .name("Giant Stance E+ 1")
                        .build()
        );

//        And
        StockItem stockItem1 = stockItems.save(
                stockItems.defaultData()
                        .product(product)
                        .identifier("S1123")
                        .build()
        );
        StockItem stockItem2 = stockItems.save(
                stockItems.defaultData()
                        .product(product)
                        .identifier("S1124")
                        .build()
        );

//        When
        Order order = orders.save(
                orders.defaultData()
                        .customer(customer)
                        .orderDate(TODAY)
                        .build()
        );

//        and
        OrderLine orderLine1 = orderLines.save(
                orderLines.defaultData()
                        .order(order)
                        .stockItem(stockItem1)
                        .endsAt(IN_TWO_DAYS)
                        .build()
        );

        OrderLine orderLine2 = orderLines.save(
                orderLines.defaultData()
                        .order(order)
                        .stockItem(stockItem2)
                        .endsAt(IN_THREE_DAYS)
                        .build()
        );

//        Then
        Order savedOrder = dataManager.load(Id.of(order)).one();

//        and
        assertThat(savedOrder)
                .hasCustomer(customer);

        assertThat(savedOrder)
                .hasOnlyOrderLines(orderLine1, orderLine2);

    }
}
