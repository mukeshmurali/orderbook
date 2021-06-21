package com.exchange;

import com.exchange.model.OrderEntry;
import com.exchange.model.Side;

public interface OrderHandler {

    void addOrder(String symbol, OrderEntry orderEntry);

    double getPrice(String symbol, int quantity, Side side);

    void modifyOrder(String symbol, OrderEntry orderEntry, OrderEntry newOrderEntry, Side side);

    void removeOrder(String symbol, OrderEntry newOrderEntryBid, Side bid);
}
