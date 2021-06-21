package com.exchange;

import java.util.HashMap;
import java.util.Map;

public class OrderBookMap {

    private static OrderBookMap orderBookMap;
    Map<String, OrderBook> map = new HashMap<>();

    public OrderBookMap() {
    }

    public static synchronized OrderBookMap getInstance() {
        if (orderBookMap == null) {
            orderBookMap = new OrderBookMap();
        }
        return orderBookMap;
    }

    public OrderBook getOrderBook(String symbol) {
        map.putIfAbsent(symbol, new OrderBook(symbol));
        return map.get(symbol);
    }
}
