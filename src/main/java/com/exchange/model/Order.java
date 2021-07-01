package com.exchange.model;

import java.util.UUID;

public class Order {
    private final Side side;
    private final String symbol;
    private int quantity;
    private double price;
    private UUID orderId;

    public Order(Side side, String symbol, int quantity, double price) {
        this.side = side;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.orderId = new UUID(System.nanoTime(),System.nanoTime());
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        Order other = (Order) obj;
        if (getOrderId() != other.getOrderId()) return false;
        return true;
    }

    public String getDetails() {
        return "OrderId : " + orderId + " Side : " + side + " symbol : " + symbol + " quantity : " + quantity + " price : " + price;
    }
}
