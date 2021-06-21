package com.exchange.model;

public class AggregatedOrderEntry {
    private int orderCount;
    private Integer summedQuantity;
    private Double price;
    private Integer level;

    public void addOrderCount(int size) {
        this.orderCount = size;
    }

    public void addQuantities(Integer summedQuantity) {
        this.summedQuantity = summedQuantity;
    }

    public void addPrice(Double price) {
        this.price = price;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public Integer getSummedQuantity() {
        return summedQuantity;
    }

    public Double getPrice() {
        return price;
    }
}
