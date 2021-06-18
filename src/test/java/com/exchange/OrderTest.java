package com.exchange;


import com.exchange.model.Order;
import com.exchange.model.Side;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {


    @BeforeEach
    void setUp() {


    }

    @Test
    void orderAdditionToAskList() {
        String symbol="IGG";
        Order order=new Order(Side.ASK,"IGG",5,30);
        OrderHandler orderHandler=new OrderBookHandler();
        Long currentTimeStamp=Instant.now().toEpochMilli();
        orderHandler.addOrder(symbol,currentTimeStamp,order);
        OrderBook orderBook= ((OrderBookHandler) orderHandler).getOrderBook(symbol);
        Order order1=orderBook.getOrder(currentTimeStamp, Side.ASK);
        assertEquals(order1.getPrice(),30);
    }



    @Test
    void orderAdditionToBidList() { fail(); }

    @Test
    void isBidListSorted() {fail(); }

    @Test
    void isAskListSorted() { fail();}

    @Test
    void addOrdersAndCheckGetPrice() { fail();}

    @Test
    void addOrdersAndCheckGetPriceHeavyUsage() { fail();}

    @Test
    void addOrdersAndCheckForMatchSuccess() {
        //Choose orders according to timestamp.
        //Generate trade
        //Remove those orders from orderbook
        fail();
    }

    @Test
    void addOrdersAndCheckForMatchFailure() {
        //Mark those orders as Resting orders
        fail();
    }

    @Test
    void modifyRestingOrdersAndCheckifMatchSuccess() {
        fail();
    }

    @Test
    void checkIfRestingOrdersModifiedLessThan4TimesCantBeRemoved(){
        fail();
    }

    @Test
    void checkIfRestingOrdersModifiedMoreThan4TimesCanBeRemoved() {
        fail();
    }

    @Test
    void addOrdersAndCheckForMatchFailureAndRemoveFailure() { fail();}

    @Test
    void addOrdersAndCheckForMatchFailureAndRemoveSuccess() { fail();}


    @Test
    void checkForTimeComplexity(){fail();}



    @AfterEach
    void tearDown() {
    }
}
