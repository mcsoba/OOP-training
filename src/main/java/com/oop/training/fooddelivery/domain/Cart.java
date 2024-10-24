
package com.oop.training.fooddelivery.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {

    private BigDecimal price;

    private List<OrderItem> orderItems;

    public Cart(){
        this.price = BigDecimal.ZERO;
        this.orderItems = new ArrayList<>();
    }


    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;

    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return Objects.equals(price, cart.price) && Objects.equals(orderItems, cart.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, orderItems);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "price=" + price +
                ", orderItems=" + orderItems +
                '}';
    }
}
