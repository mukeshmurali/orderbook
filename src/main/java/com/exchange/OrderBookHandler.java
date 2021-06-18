package com.exchange;

import com.exchange.model.Order;
import com.exchange.model.Side;

import java.util.HashMap;
import java.util.Map;

public class OrderBookHandler implements OrderHandler {
    OrderBookMap orderBookMap;


    public OrderBookHandler() {
      orderBookMap=OrderBookMap.getInstance();
    }

    @Override
    public void addOrder(String symbol, long currentTimeStamp, Order order) {
        OrderBook orderBook=getOrderBook(symbol);
        orderBook.addOrder(currentTimeStamp,order);
    }

    public OrderBook getOrderBook(String symbol) {
        return orderBookMap.getOrderBook(symbol);
    }

    @Override
    public double getPrice(String symbol, int quantity, Side ask) {
        return 0;
    }
}
