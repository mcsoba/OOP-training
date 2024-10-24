package com.oop.training.fooddelivery.data;

import java.util.List;

import com.oop.training.fooddelivery.domain.Customer;
import com.oop.training.fooddelivery.domain.Food;
import com.oop.training.fooddelivery.domain.Order;

public interface DataStore {
    List<Customer> getCustomers();

    List<Order> getOrders();

    List<Food> getFoods();

    Order createOrder(Order order);
}
