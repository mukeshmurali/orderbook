package com.exchange.model;

public class OrderEntry {
    private final Order order;
    private final long timeStamp;
    private Boolean restingOrder;
    private Integer modificationCount = 0;

    public OrderEntry(OrderEntry orderEntry, int modificationCount) {
        this.order = orderEntry.order;
        this.timeStamp = orderEntry.getTimeStamp();
        this.restingOrder = orderEntry.getRestingOrder();
        this.modificationCount = modificationCount;
    }

    public OrderEntry(Order order, Long timeStamp) {
        this.order = order;
        this.timeStamp = timeStamp;
        this.restingOrder = true;
    }

    public Boolean getRestingOrder() {
        return restingOrder;
    }

    public void setRestingOrder(Boolean restingOrder) {
        this.restingOrder = restingOrder;
    }

    public Integer getModificationCount() {
        return modificationCount;
    }

    public void setModificationCount(Integer modificationCount) {
        this.modificationCount = modificationCount;
    }

    public Double getPrice() {
        return this.order.getPrice();
    }

    public Long getTimeStamp() {
        return this.timeStamp;
    }

    public Order getOrder() {
        return order;
    }
}
