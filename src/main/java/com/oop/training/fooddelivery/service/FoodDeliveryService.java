
package com.oop.training.fooddelivery.service;

import com.oop.training.fooddelivery.domain.Customer;
import com.oop.training.fooddelivery.domain.Food;
import com.oop.training.fooddelivery.domain.Order;
import com.oop.training.fooddelivery.domain.User;

import java.util.List;

public interface FoodDeliveryService {
    Customer authenticate(User user) throws AuthenticationException;

    Order createOrder(Customer customer);

    List<Food> listAllFood();

    void updateCart(Customer customer, Food food, int pieces);
}
