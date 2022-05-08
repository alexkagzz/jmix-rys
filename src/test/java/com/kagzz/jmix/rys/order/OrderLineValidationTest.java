package com.kagzz.jmix.rys.order;

import com.kagzz.jmix.rys.order.entity.Order;
import com.kagzz.jmix.rys.order.entity.OrderLine;
import com.kagzz.jmix.rys.product.entity.StockItem;
import com.kagzz.jmix.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderLineValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations validations;

    private OrderLine orderLine;
    private final LocalDateTime NOW = LocalDateTime.now();
    private final LocalDateTime In_FIVE_MINUTES = NOW.plusMinutes(5);
    private final LocalDateTime YESTERDAY = NOW.minusDays(1);
    private final LocalDateTime TOMORROW = NOW.plusDays(1);
    private final LocalDateTime IN_TWO_DAYS = NOW.plusDays(2);

    @BeforeEach
    void setUp() {
        orderLine = dataManager.create(OrderLine.class);
    }

    @Test
    void given_validOrderLine_when_validate_then_noViolationOccurs() {

//        Given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setStartsAt(In_FIVE_MINUTES);
        orderLine.setEndsAt(IN_TWO_DAYS);

//        Expect
        validations.assertNoViolations(orderLine);

    }

    @Test
    void given_orderLineWithoutDate_when_validate_then_oneViolationOccurs() {

//        Given
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setStartsAt(In_FIVE_MINUTES);
        orderLine.setEndsAt(IN_TWO_DAYS);

//        And
        orderLine.setOrder(null);


//        Expect
        validations.assertExactlyOneViolationWith(orderLine, "order", "NotNull");

    }

    @Test
    void given_orderLineWithoutAStockItem_when_validate_then_oneViolationOccurs() {

//        Given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStartsAt(In_FIVE_MINUTES);
        orderLine.setEndsAt(IN_TWO_DAYS);

//        And
        orderLine.setStockItem(null);

//        Expect
        validations.assertExactlyOneViolationWith(orderLine, "stockItem", "NotNull");
    }


    @Test
    void given_orderLineWithStartDateInThePast_when_validate_then_oneViolationOccurs() {

//        Given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setEndsAt(IN_TWO_DAYS);

//        And
        orderLine.setStartsAt(YESTERDAY);

//        Expect
        validations.assertOneViolationWith(orderLine, "startsAt", "FutureOrPresent");
    }


    @Test
    void given_orderLineWithEndDateInThePast_when_validate_then_oneViolationOccurs() {

//        Given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setStartsAt(IN_TWO_DAYS);

//        And
        orderLine.setEndsAt(YESTERDAY);

//        Expect
        validations.assertOneViolationWith(orderLine, "endsAt", "FutureOrPresent");
    }

    @Test
    void given_orderLineWithStartDateNotPresent_when_validate_then_oneViolationOccurs() {

//        Given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setEndsAt(IN_TWO_DAYS);

//        And
        orderLine.setStartsAt(null);

//        Expect
        validations.assertOneViolationWith(orderLine, "startsAt", "NotNull");
    }


    @Test
    void given_orderLineWithEndDateNotPresent_when_validate_then_oneViolationOccurs() {

//        Given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setStartsAt(IN_TWO_DAYS);

//        And
        orderLine.setEndsAt(null);


//        Expect
        validations.assertOneViolationWith(orderLine, "endsAt", "NotNull");

    }

    @Test
    void given_orderLineWithEndDateBeforeStartDate_when_validate_then_oneViolationOccurs() {

//        Given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));

//        And
        orderLine.setStartsAt(IN_TWO_DAYS);
        orderLine.setEndsAt(TOMORROW);


//        Expect
        validations.assertOneViolationWith(orderLine, "ValidRentalPeriod");

    }

}