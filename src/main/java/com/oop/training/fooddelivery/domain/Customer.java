package com.oop.training.fooddelivery.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer extends User {

    private long id;
    private String name;
    private BigDecimal balance;
    private List<Order> orderItems;
    private Cart cart;

    public Customer(){
        this.cart = new Cart();
        this.orderItems = new ArrayList<>();
    }

    public Customer(long id, String name, BigDecimal balance, List<Order> orderItems, Cart cart) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.orderItems = orderItems;
        this.cart = cart;
    }

    public long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Order> getOrders() {
        return this.orderItems;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Order> getOrderItems() {
        return orderItems;
    }

    public void setOrders(List<Order> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(name, customer.name) && Objects.equals(balance, customer.balance) && Objects.equals(orderItems, customer.orderItems) && Objects.equals(cart, customer.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance, orderItems, cart);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", orderItems=" + orderItems +
                ", cart=" + cart +
                '}';
    }
}
