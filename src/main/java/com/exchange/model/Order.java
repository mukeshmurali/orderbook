package com.exchange.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Order {
    private final Side side;
    private final String symbol;
    private final int quantity;
    private final double price;
    private UUID orderId;

    public Order(Side side, String symbol, int quantity, double price) {
        this.side=side;
        this.symbol=symbol;
        this.quantity=quantity;
        this.price=price;
        this.orderId=UUID.randomUUID();
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Side getSide() {
        return side;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
