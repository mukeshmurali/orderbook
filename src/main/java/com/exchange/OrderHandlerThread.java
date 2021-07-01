package com.exchange;

import com.exchange.model.Order;
import com.exchange.model.OrderEntry;
import com.exchange.model.Side;

public class OrderHandlerThread implements Runnable{
    private OrderHandler orderHandler;
    private OrderBook orderBook;

    public OrderHandlerThread() {
        orderHandler=new OrderBookHandler();
    }

    @Override
    public void run() {
        System.out.println("Starting to run");
        String symbol = "IGG";
        Order orderBid = new Order(Side.BUY, "IGG", 3, 13);
        Order orderAsk = new Order(Side.SELL, "IGG", 3, 14);
        Order newOrderBid = new Order(Side.BUY, "IGG", 3, 14);
        Long currentTimeStampAsk = System.nanoTime();
        OrderEntry orderEntryAsk = new OrderEntry(orderAsk, currentTimeStampAsk);
        Long currentTimeStampBid = System.nanoTime();
        OrderEntry orderEntryBid = new OrderEntry(orderBid, currentTimeStampBid);
        Long newCurrentTimeStampBid = System.nanoTime();
        OrderEntry newOrderEntryBid = new OrderEntry(newOrderBid, newCurrentTimeStampBid);
        orderHandler.addOrder(symbol, orderEntryAsk);
        orderHandler.addOrder(symbol, orderEntryBid);
        orderHandler.modifyOrder(symbol, orderEntryBid, newOrderEntryBid, Side.BUY);
        orderBook= ((OrderBookHandler) orderHandler).getOrderBook(symbol);
        orderBook.performMatch();
        System.out.println("Test finished");
    }
}
