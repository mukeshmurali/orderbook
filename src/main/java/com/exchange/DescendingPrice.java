package com.exchange;

import com.exchange.model.OrderEntry;

import java.util.Comparator;

public class DescendingPrice implements Comparator<OrderEntry> {
    @Override
    public int compare(OrderEntry o1, OrderEntry o2) {
        if (Double.compare(o2.getPrice(), o1.getPrice()) == 0)
            return Long.compare(o1.getTimeStamp(), o2.getTimeStamp());
        else return Double.compare(o2.getPrice(), o1.getPrice());
    }
}
