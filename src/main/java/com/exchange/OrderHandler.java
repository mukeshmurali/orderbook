package com.exchange;

import com.exchange.model.Order;
import com.exchange.model.Side;

public interface OrderHandler {

    void addOrder(String symbol, long currentTimeStamp, Order order);

    double getPrice(String symbol, int quantity, Side ask);
}
