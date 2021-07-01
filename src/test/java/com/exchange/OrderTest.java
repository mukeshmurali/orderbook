package com.exchange;


import com.exchange.model.Order;
import com.exchange.model.OrderEntry;
import com.exchange.model.Side;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private OrderHandler orderHandler;
    private OrderBook orderBook;

    @BeforeEach
    void setUp() {
        orderHandler = new OrderBookHandler();
    }

    @Test
    void orderAdditionToAskMap() {
        String symbol = "IGG";
        Order order = new Order(Side.SELL, "IGG", 5, 30);
        Long currentTimeStamp = getCurrentTimeStamp();
        OrderEntry orderEntry = new OrderEntry(order, currentTimeStamp);
        orderHandler.addOrder(symbol, orderEntry);
        orderBook = ((OrderBookHandler) orderHandler).getOrderBook(symbol);
        assertTrue(orderBook.getAskMap().containsKey(orderEntry));
    }


    @Test
    void orderAdditionToBidMap() {
        String symbol = "IGG";
        Order order = new Order(Side.BUY, "IGG", 3, 13);
        Long currentTimeStamp = getCurrentTimeStamp();
        OrderEntry orderEntry = new OrderEntry(order, currentTimeStamp);
        orderHandler.addOrder(symbol, orderEntry);
        orderBook = ((OrderBookHandler) orderHandler).getOrderBook(symbol);
        assertTrue(orderBook.getBidMap().containsKey(orderEntry));
    }

    @Test
    void multipleOrderAdditionToAskAndBidMaps() {
        String symbol = "IGG";
        Order orderBid = new Order(Side.BUY, "IGG", 3, 13);
        Order orderAsk = new Order(Side.SELL, "IGG", 5, 30);
        Long currentTimeStampAsk = getCurrentTimeStamp();
        OrderEntry orderEntryAsk = new OrderEntry(orderAsk, currentTimeStampAsk);
        Long currentTimeStampBid = getCurrentTimeStamp();
        OrderEntry orderEntryBid = new OrderEntry(orderBid, currentTimeStampBid);
        orderHandler.addOrder(symbol, orderEntryAsk);
        orderHandler.addOrder(symbol, orderEntryBid);
        orderBook = ((OrderBookHandler) orderHandler).getOrderBook(symbol);
        assertTrue(orderBook.getAskMap().containsKey(orderEntryAsk));
        assertTrue(orderBook.getBidMap().containsKey(orderEntryBid));
    }

    @Test
    void addOrdersAndCheckGetPriceForAskOrder() {
        String symbol = "IGG";
        Order orderAsk1 = new Order(Side.SELL, "IGG", 5, 30);
        Order orderAsk2 = new Order(Side.SELL, "IGG", 10, 28);
        Order orderAsk3 = new Order(Side.SELL, "IGG", 10, 28);
        Order orderAsk4 = new Order(Side.SELL, "IGG", 3, 28);
        Order orderAsk5 = new Order(Side.SELL, "IGG", 7, 25);
        Long currentTimeStampAsk1 = getCurrentTimeStamp();
        OrderEntry orderEntryAsk1 = new OrderEntry(orderAsk1, currentTimeStampAsk1);
        Long currentTimeStampAsk2 = getCurrentTimeStamp();
        OrderEntry orderEntryAsk2 = new OrderEntry(orderAsk2, currentTimeStampAsk2);
        Long currentTimeStampAsk3 = getCurrentTimeStamp();
        OrderEntry orderEntryAsk3 = new OrderEntry(orderAsk3, currentTimeStampAsk3);
        Long currentTimeStampAsk4 = getCurrentTimeStamp();
        OrderEntry orderEntryAsk4 = new OrderEntry(orderAsk4, currentTimeStampAsk4);
        Long currentTimeStampAsk5 = getCurrentTimeStamp();
        OrderEntry orderEntryAsk5 = new OrderEntry(orderAsk5, currentTimeStampAsk5);
        orderHandler.addOrder(symbol, orderEntryAsk1);
        orderHandler.addOrder(symbol, orderEntryAsk2);
        orderHandler.addOrder(symbol, orderEntryAsk3);
        orderHandler.addOrder(symbol, orderEntryAsk4);
        orderHandler.addOrder(symbol, orderEntryAsk5);
        assertEquals(orderHandler.getPrice(symbol, 17, Side.SELL), 28.58823529411765);
    }

    @Test
    void addOrdersAndCheckGetPriceForBidOrder() {
        String symbol = "IGG";
        Order orderBid1 = new Order(Side.BUY, "IGG", 3, 13);
        Order orderBid2 = new Order(Side.BUY, "IGG", 5, 12);
        Long currentTimeStampBid1 = getCurrentTimeStamp();
        OrderEntry orderEntryBid1 = new OrderEntry(orderBid1, currentTimeStampBid1);
        Long currentTimeStampBid2 = getCurrentTimeStamp();
        OrderEntry orderEntryBid2 = new OrderEntry(orderBid2, currentTimeStampBid2);
        orderHandler.addOrder(symbol, orderEntryBid1);
        orderHandler.addOrder(symbol, orderEntryBid2);
        assertEquals(orderHandler.getPrice(symbol, 2, Side.BUY), 13);
    }

    @Test
    void addOrdersAndPerformMatchAndCheckIfMatchedOrdersAreRemoved() {
        String symbol = "IGG";
        Order orderBid = new Order(Side.BUY, "IGG", 3, 13);
        Order orderAsk = new Order(Side.SELL, "IGG", 3, 13);
        Long currentTimeStampAsk = getCurrentTimeStamp();
        OrderEntry orderEntryAsk = new OrderEntry(orderAsk, currentTimeStampAsk);
        Long currentTimeStampBid = getCurrentTimeStamp();
        OrderEntry orderEntryBid = new OrderEntry(orderBid, currentTimeStampBid);
        orderHandler.addOrder(symbol, orderEntryAsk);
        orderHandler.addOrder(symbol, orderEntryBid);
        orderBook = ((OrderBookHandler) orderHandler).getOrderBook(symbol);
        orderBook.performMatch();
        assertFalse(orderBook.getAskMap().containsKey(orderEntryAsk));
        assertFalse(orderBook.getBidMap().containsKey(orderEntryBid));
    }


    @Test
    void modifyRestingOrdersAndPerformMatchAndCheckIfMatchedOrdersAreRemoved() {
        String symbol = "IGG";
        Order orderBid = new Order(Side.BUY, "IGG", 3, 13);
        Order orderAsk = new Order(Side.SELL, "IGG", 3, 14);
        Order newOrderBid = new Order(Side.BUY, "IGG", 3, 14);
        Long currentTimeStampAsk = getCurrentTimeStamp();
        OrderEntry orderEntryAsk = new OrderEntry(orderAsk, currentTimeStampAsk);
        Long currentTimeStampBid = getCurrentTimeStamp();
        OrderEntry orderEntryBid = new OrderEntry(orderBid, currentTimeStampBid);
        Long newCurrentTimeStampBid = getCurrentTimeStamp();
        OrderEntry newOrderEntryBid = new OrderEntry(newOrderBid, newCurrentTimeStampBid);
        orderHandler.addOrder(symbol, orderEntryAsk);
        orderHandler.addOrder(symbol, orderEntryBid);
        orderHandler.modifyOrder(symbol, orderEntryBid, newOrderEntryBid, Side.BUY);
        orderBook = ((OrderBookHandler) orderHandler).getOrderBook(symbol);
        assertFalse(orderBook.getBidMap().containsKey(orderEntryBid));
        assertTrue(orderBook.getBidMap().containsKey(newOrderEntryBid));
        orderBook.performMatch();
        assertFalse(orderBook.getBidMap().containsKey(newOrderEntryBid));
    }


    @Test
    void checkIfRestingOrdersModifiedLessThan4TimesCantBeRemoved() {
        String symbol = "IGG";
        Order orderBid = new Order(Side.BUY, "IGG", 3, 13);
        Order orderAsk = new Order(Side.SELL, "IGG", 3, 14);
        Order newOrderBid = new Order(Side.BUY, "IGG", 3, 14);
        Long currentTimeStampAsk = getCurrentTimeStamp();
        OrderEntry orderEntryAsk = new OrderEntry(orderAsk, currentTimeStampAsk);
        Long currentTimeStampBid = getCurrentTimeStamp();
        OrderEntry orderEntryBid = new OrderEntry(orderBid, currentTimeStampBid);
        Long newCurrentTimeStampBid = getCurrentTimeStamp();
        OrderEntry orderEntryBid1 = new OrderEntry(newOrderBid, newCurrentTimeStampBid);
        orderHandler.addOrder(symbol, orderEntryAsk);
        orderHandler.addOrder(symbol, orderEntryBid);
        orderHandler.modifyOrder(symbol, orderEntryBid, orderEntryBid1, Side.BUY);
        OrderEntry orderEntryBid2 = new OrderEntry(orderEntryBid1, 2);
        orderEntryBid2.getOrder().setPrice(17);
        orderHandler.modifyOrder(symbol, orderEntryBid1, orderEntryBid2, Side.BUY);
        OrderEntry orderEntryBid3 = new OrderEntry(orderEntryBid2, 3);
        orderEntryBid2.getOrder().setPrice(18);
        orderHandler.modifyOrder(symbol, orderEntryBid2, orderEntryBid3, Side.BUY);
        orderHandler.removeOrder(symbol, orderEntryBid3, Side.BUY);
        orderBook = ((OrderBookHandler) orderHandler).getOrderBook(symbol);
        assertTrue(orderBook.getBidMap().containsKey(orderEntryBid3));
        OrderEntry orderEntryBid4 = new OrderEntry(orderEntryBid3, 4);
        orderEntryBid2.getOrder().setPrice(19);
        orderHandler.modifyOrder(symbol, orderEntryBid3, orderEntryBid4, Side.BUY);
        orderHandler.removeOrder(symbol, orderEntryBid4, Side.BUY);
        assertFalse(orderBook.getBidMap().containsKey(orderEntryBid4));
    }


    @Test
    void loadTest() throws InterruptedException {
//        load(new OrderHandlerThread(), 100, 100);
    }

    private void load(Runnable runnable, int threadCount, int timeOut) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < 100; i++) {
            IntStream.range(0, threadCount).forEach(j -> executor.submit(runnable));
            Thread.sleep(10);
        }
        executor.shutdown();
//            executor.awaitTermination(timeOut, TimeUnit.SECONDS);
    }

    private Long getCurrentTimeStamp() {
        return System.nanoTime();
    }

    @AfterEach
    void tearDown() {
    }
}
