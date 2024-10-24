package com.oop.training.fooddelivery.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Order {

    private Long orderId;
    private long customerId;
    private BigDecimal price;
    private LocalDateTime timestampCreated;
    private List<OrderItem> orderItems;
    public Order() {

    }

    public long getOrderId() {
        return this.orderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getTimestampCreated() {
        return timestampCreated;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(long customerId) {
        this.customerId=customerId;
    }


    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setTimestampCreated(LocalDateTime timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return customerId == order.customerId && Objects.equals(orderId, order.orderId) && Objects.equals(price, order.price) && Objects.equals(timestampCreated, order.timestampCreated) && Objects.equals(orderItems, order.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, price, timestampCreated, orderItems);
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", price=" + price +
                ", timestampCreated=" + timestampCreated +
                ", orderItems=" + orderItems +
                '}';
    }
}
