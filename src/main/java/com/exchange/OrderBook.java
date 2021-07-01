package com.exchange;

import com.exchange.model.AggregatedOrderEntry;
import com.exchange.model.Order;
import com.exchange.model.OrderEntry;
import com.exchange.model.Side;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class OrderBook {
    private final String symbol;
    Map<OrderEntry, UUID> askMap;
    Map<OrderEntry, UUID> bidMap;


    public OrderBook(String symbol) {
        this.symbol = symbol;
        askMap = new ConcurrentSkipListMap<OrderEntry, UUID>(new DescendingPrice());
        bidMap = new ConcurrentSkipListMap<OrderEntry, UUID>(new DescendingPrice());
    }

    public String getSymbol() {
        return symbol;
    }

    public ConcurrentSkipListMap<OrderEntry, UUID> getAskMap() {
        return (ConcurrentSkipListMap<OrderEntry, UUID>) askMap;
    }

    public ConcurrentSkipListMap<OrderEntry, UUID> getBidMap() {
        return (ConcurrentSkipListMap<OrderEntry, UUID>) bidMap;
    }

    public void addOrder(OrderEntry orderEntry) {
        if (orderEntry.getOrder().getSide() == Side.SELL) {
            askMap.put(orderEntry, orderEntry.getOrder().getOrderId());
        } else {
            bidMap.put(orderEntry, orderEntry.getOrder().getOrderId());
        }
    }


    public ArrayList<AggregatedOrderEntry> getAggregatedOrders(Map<OrderEntry, UUID> askMap) {
        ArrayList<AggregatedOrderEntry> aggregatedOrderEntries = new ArrayList<>();
        AggregatedOrderEntry aggregatedOrderEntry;
        Map<Double, List<OrderEntry>> byPrice = askMap.keySet().stream().collect(Collectors.groupingBy(OrderEntry::getPrice, LinkedHashMap::new, toList()));
        Integer level = 1;
        for (Map.Entry<Double, List<OrderEntry>> priceKey : byPrice.entrySet()) {
            List<OrderEntry> orderEntryList = byPrice.get(priceKey.getKey());
            aggregatedOrderEntry = new AggregatedOrderEntry();
            aggregatedOrderEntry.addOrderCount(orderEntryList.size());
            aggregatedOrderEntry.addQuantities(orderEntryList.stream().map(m -> m.getOrder().getQuantity()).reduce(0, (a, b) -> a + b));
            aggregatedOrderEntry.addPrice(priceKey.getKey());
            aggregatedOrderEntry.setLevel(level);
            aggregatedOrderEntries.add(aggregatedOrderEntry);
            level++;
        }
        return aggregatedOrderEntries;
    }


    public double getPrice(int quantity, ArrayList<AggregatedOrderEntry> aggregatedOrderEntries) {
        Double priceForQuantity = 0d;
        int claimedQuantity = 0;
        claimedQuantity = quantity;
        for (AggregatedOrderEntry aggregatedOrder : aggregatedOrderEntries) {
            if (aggregatedOrder.getSummedQuantity() >= claimedQuantity && claimedQuantity > 0) {
                priceForQuantity = priceForQuantity + claimedQuantity * aggregatedOrder.getPrice();
                claimedQuantity = 0;
                break;
            }
            if (aggregatedOrder.getSummedQuantity() < claimedQuantity && claimedQuantity > 0) {
                priceForQuantity = priceForQuantity + aggregatedOrder.getPrice() * aggregatedOrder.getSummedQuantity();
                claimedQuantity = claimedQuantity - aggregatedOrder.getSummedQuantity();
            }
        }
        return priceForQuantity / quantity;
    }

    public void performMatch() {
        for (Map.Entry<OrderEntry, UUID> askOrderEntry : askMap.entrySet()) {
            Order askOrder = askOrderEntry.getKey().getOrder();
            for (Map.Entry<OrderEntry, UUID> bidOrderEntry : bidMap.entrySet().stream().filter(map -> map.getKey().getPrice() == askOrder.getPrice() &&   map.getKey().getOrder().getQuantity() == askOrder.getQuantity()).collect(Collectors.toMap(map-> map.getKey(),map -> map.getValue())).entrySet()) {
                Order bidOrder = bidOrderEntry.getKey().getOrder();
                    System.out.println("Trade generated for " + askOrder.getDetails() + "  : " + bidOrder.getDetails());
                    removeOrderAfterMatch(askOrderEntry.getKey(), Side.SELL);
                    removeOrderAfterMatch(bidOrderEntry.getKey(), Side.BUY);
            }
        }
    }

    public void removeOrder(OrderEntry orderEntry, Side side) {
        if (orderEntry.getModificationCount() < 4) return;
        if (side == Side.SELL) {
            askMap.remove(orderEntry);
        } else {
            bidMap.remove(orderEntry);
        }
    }

    public void removeOrderAfterMatch(OrderEntry orderEntry, Side side) {
        if (side == Side.SELL) {
            askMap.remove(orderEntry);
        } else {
            bidMap.remove(orderEntry);
        }
    }

    public void modifyOrder(OrderEntry orderEntry, OrderEntry newOrderEntry, Side side) {
        Integer currentModificationCount = orderEntry.getModificationCount();
        if (side == Side.SELL) {
            askMap.remove(orderEntry);
            addOrder(new OrderEntry(newOrderEntry, currentModificationCount + 1));
        } else {
            bidMap.remove(orderEntry);
            addOrder(newOrderEntry);
        }
    }
}

