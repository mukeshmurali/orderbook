package com.exchange;

import com.exchange.model.Order;
import com.exchange.model.Side;

import java.util.Hashtable;

public class OrderBook {
    private final String symbol;
    Hashtable<Long, Order> askTable;
    Hashtable<Long, Order> bidTable;

    public OrderBook(String symbol) {
        this.symbol = symbol;
        askTable = new Hashtable<>();
        bidTable = new Hashtable<>();
    }

    public String getSymbol() {
        return symbol;
    }

    public Hashtable<Long, Order> getAskTable() {
        return askTable;
    }

    public Hashtable<Long, Order> getBidTable() {
        return bidTable;
    }

    public void addOrder(long currentTimeStamp, Order order) {
        if (order.getSide() == Side.ASK) {
            askTable.put(currentTimeStamp, order);
        } else {
            bidTable.put(currentTimeStamp, order);
        }
    }

    public Order getOrder(Long currentTimeStamp, Side side) {
        if(side==Side.ASK) {
            return getAskTable().get(currentTimeStamp);
        }else{
            return getBidTable().get(currentTimeStamp);
        }
    }
}
