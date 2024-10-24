package com.oop.training.fooddelivery.service;

import com.oop.training.fooddelivery.data.DataStore;
import com.oop.training.fooddelivery.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DefaultFoodDeliveryService implements FoodDeliveryService {

    private final DataStore dataStore;
    public DefaultFoodDeliveryService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Customer authenticate(User user) throws AuthenticationException {
            List<Customer> customers = dataStore.getCustomers();

                    for (Customer customer : customers) {
                if (customer.getEmail().equals(user.getEmail()) && customer.getPassword().equals(user.getPassword())) {
                    return customer;
                }
            }

        throw new AuthenticationException("Authentication failed. Invalid credentials.");
    }

    @Override
    public Order createOrder(Customer customer) throws LowBalanceException, IllegalStateException {
        if (customer.getCart().getOrderItems().isEmpty()) {
            throw new IllegalStateException("Cannot create an order with an empty cart.");
        }

        BigDecimal totalPrice = customer.getCart().getPrice();

        if (totalPrice == null) {
            throw new IllegalStateException("Total price of cart is null. Please check your calculations.");
        }

        if (customer.getBalance().compareTo(totalPrice) < 0) {
            throw new LowBalanceException("You don't have enough money, your balance is only " + customer.getBalance() +" EUR. We do not empty your cart, please remove some of the items.");
        }

        Order newOrder = new Order();
        newOrder.setOrderId(generateOrderId());
        newOrder.setCustomerId(customer.getId());
        newOrder.setTimestampCreated(LocalDateTime.now());
        newOrder.setPrice(totalPrice);
        newOrder.setOrderItems(customer.getCart().getOrderItems());

        List<Order> customerOrdersCopy = new ArrayList<>(customer.getOrders());
        customerOrdersCopy.add(newOrder);
        customer.setOrders(customerOrdersCopy);
        dataStore.createOrder(newOrder);

        customer.setBalance(customer.getBalance().subtract(totalPrice));
        customer.getCart().getOrderItems().clear();

        return newOrder;
    }

    private Long generateOrderId() {
        Long maxOrderId = dataStore.getOrders().stream()
                .mapToLong(Order::getOrderId)
                .max()
                .orElse(0L);
        return maxOrderId + 1;
    }


    @Override
    public List<Food> listAllFood() {
        return null;
    }

    @Override
    public void updateCart(Customer customer, Food food, int pieces) throws LowBalanceException {
        if (pieces < 0) {
            throw new IllegalArgumentException("Invalid input: pieces cannot be negative.");
        }
        if (pieces == 0) {
            if (!isFoodInCart(customer, food)) {
                throw new IllegalArgumentException("Invalid input: pieces is 0 and the given food is not in the cart.");
            } else {
                removeFoodFromCart(customer, food);
                updateCartPrice(customer);
                return;
            }
        }

            Cart cart = customer.getCart();
            BigDecimal totalPrice = food.getPrice().multiply(BigDecimal.valueOf(pieces));
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(food);
            orderItem.setPieces(pieces);
            orderItem.setPrice(totalPrice);

            if (isFoodInCart(customer, food)) {
                for (OrderItem existingItem : cart.getOrderItems()) {
                    if (existingItem.getFood().equals(food)) {
                        cart.setPrice(cart.getPrice().subtract(existingItem.getPrice()));
                        existingItem.setPieces(pieces);
                        existingItem.setPrice(totalPrice);
                        cart.setPrice(cart.getPrice().add(totalPrice));
                        return;
                    }
                }
            } else {
                cart.getOrderItems().add(orderItem);
                cart.setPrice(cart.getPrice().add(totalPrice));
            }
        }

    private void removeFoodFromCart(Customer customer, Food food) {
        Cart cart = customer.getCart();
        List<OrderItem> orderItems = cart.getOrderItems();
        orderItems.removeIf(orderItem -> orderItem.getFood().equals(food));
        cart.setOrderItems(orderItems);
    }

    private void updateCartPrice(Customer customer) {
        Cart cart = customer.getCart();
        BigDecimal newPrice = calculateTotalPrice(cart.getOrderItems());
        cart.setPrice(newPrice);
    }

    private BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private boolean isFoodInCart(Customer customer, Food food) {
        if (customer == null || customer.getCart() == null || customer.getCart().getOrderItems() == null) {
            return false;
        }

        return customer.getCart().getOrderItems().stream()
                .anyMatch(orderItem -> orderItem != null && orderItem.getFood() != null && orderItem.getFood().equals(food));
    }

    public DataStore getDataStore() {
        return dataStore;
    }
}
