package com.oop.training.fooddelivery.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.oop.training.fooddelivery.domain.*;

public class FileDataStore implements DataStore {

    private final String baseDirPath;
    private List<Customer> customers;
    private List<Food> foods;
    private List<Order> orders;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final String SEPARATOR = ",";

    public FileDataStore(String baseDirectoryPath) {
        this.baseDirPath = baseDirectoryPath;
        init();
    }

    public void init() {
        foods = readFoodsFromCSV(baseDirPath + "/foods.csv");
        customers = readCustomersFromCSV(baseDirPath + "/customers.csv");
        orders = readOrdersFromCSV(baseDirPath + "/orders.csv");
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public List<Food> getFoods() {
        return foods;
    }

    @Override
    public Order createOrder(Order order) {
        orders.add(order);
        return order;
    }

    private Customer findCustomerById(long customerId) {
        return customers.stream()
                .filter(c -> c.getId() == customerId)
                .findFirst()
                .orElse(null);
    }

    private List<Customer> readCustomersFromCSV(String filePath) {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(SEPARATOR);
                Customer customer = new Customer();
                customer.setEmail(data[0]);
                customer.setPassword(data[1]);
                customer.setId(Long.parseLong(data[2]));
                customer.setName(data[3]);
                customer.setBalance(new BigDecimal(data[4]));
                customers.add(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return customers;
    }

    private List<Food> readFoodsFromCSV(String filePath) {
        List<Food> foods = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(SEPARATOR);
                Food food = new Food();
                food.setName(data[0]);
                food.setCalorie(new BigDecimal(data[1]));
                food.setDescription(data[2]);
                food.setPrice(new BigDecimal(data[3]));
                food.setCategory(Category.valueOf(data[4]));
                foods.add(food);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return foods;
    }

    private List<Order> readOrdersFromCSV(String filePath) {
        List<Order> orders = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            Order currentOrder = null;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(SEPARATOR);

                if (currentOrder == null || !data[0].equals(String.valueOf(currentOrder.getOrderId()))) {
                    // New order
                    currentOrder = new Order();
                    currentOrder.setOrderId(Long.parseLong(data[0]));
                    currentOrder.setCustomerId(Long.parseLong(data[1]));
                    currentOrder.setTimestampCreated(LocalDateTime.parse(data[5], DATE_TIME_FORMATTER));
                    currentOrder.setPrice(new BigDecimal(data[6]));
                    List<OrderItem> orderItems = new ArrayList<>();
                    OrderItem orderItem = new OrderItem();
                    orderItem.setPieces(Integer.parseInt(data[3]));
                    orderItem.setPrice(new BigDecimal(data[4]));
                    String orderItemName = data[2];
                    Food orderItemFood = findFoodByName(orderItemName);
                    orderItem.setFood(orderItemFood);
                    orderItems.add(orderItem);
                    currentOrder.setOrderItems(orderItems);
                    orders.add(currentOrder);
                } else {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setPieces(Integer.parseInt(data[3]));
                    orderItem.setPrice(new BigDecimal(data[4]));
                    String orderItemName = data[2];
                    Food orderItemFood = findFoodByName(orderItemName);
                    orderItem.setFood(orderItemFood);
                    currentOrder.getOrderItems().add(orderItem);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orders;
    }

    private Food findFoodByName(String foodName) {
        return foods.stream()
                .filter(food -> food.getName().equals(foodName))
                .findFirst()
                .orElse(null);
    }
}
