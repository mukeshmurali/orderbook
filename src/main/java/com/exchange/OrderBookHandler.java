package com.exchange;

import com.exchange.model.AggregatedOrderEntry;
import com.exchange.model.OrderEntry;
import com.exchange.model.Side;

import java.util.ArrayList;
import java.util.List;

public class OrderBookHandler implements OrderHandler {
    OrderBookMap orderBookMap;

    public OrderBookHandler() {
        orderBookMap = OrderBookMap.getInstance();
    }

    @Override
    public void addOrder(String symbol, OrderEntry orderEntry) {
        OrderBook orderBook = getOrderBook(symbol);
        orderBook.addOrder(orderEntry);
    }

    public OrderBook getOrderBook(String symbol) {
        return orderBookMap.getOrderBook(symbol);
    }

    @Override
    public double getPrice(String symbol, int quantity, Side ask) {
        OrderBook orderBook = orderBookMap.getOrderBook(symbol);
        ArrayList<AggregatedOrderEntry> aggregatedOrderEntryList;
        if (ask.equals(Side.SELL)) aggregatedOrderEntryList = orderBook.getAggregatedOrders(orderBook.getAskMap());
        else aggregatedOrderEntryList = orderBook.getAggregatedOrders(orderBook.getBidMap());
        return orderBook.getPrice(quantity, aggregatedOrderEntryList);
    }

    @Override
    public void modifyOrder(String symbol, OrderEntry orderEntry, OrderEntry newOrderEntry, Side side) {
        OrderBook orderBook = orderBookMap.getOrderBook(symbol);
        orderBook.modifyOrder(orderEntry, newOrderEntry, side);
    }

    @Override
    public void removeOrder(String symbol, OrderEntry orderEntry, Side side) {
        OrderBook orderBook = orderBookMap.getOrderBook(symbol);
        orderBook.removeOrder(orderEntry, side);
    }
}
