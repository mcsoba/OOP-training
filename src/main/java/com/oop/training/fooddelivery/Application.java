package com.oop.training.fooddelivery;

import com.oop.training.fooddelivery.data.DataStore;
import com.oop.training.fooddelivery.data.FileDataStore;
import com.oop.training.fooddelivery.domain.*;
import com.oop.training.fooddelivery.service.AuthenticationException;
import com.oop.training.fooddelivery.service.DefaultFoodDeliveryService;
import com.oop.training.fooddelivery.service.LowBalanceException;
import com.oop.training.fooddelivery.view.CLIView;
import com.oop.training.fooddelivery.view.View;

public class Application {

    private final DefaultFoodDeliveryService service;
    private final View view;

    private Application(String path) {
        DataStore dataStore = new FileDataStore(path);
        service = new DefaultFoodDeliveryService(dataStore);
        view = new CLIView();
    }
    public static void main(String[] args) {
        Application application = new Application("input");   //args[0]
        application.start();
    }

    private void start() {
        User user = view.readCredentials();
        Customer authenticatedCustomer = service.authenticate(user);
        try {
            if (authenticatedCustomer != null) {
                view.printWelcomeMessage(authenticatedCustomer);
            }
        } catch (AuthenticationException e) {
            view.printErrorMessage("Authentication failed.");
            System.exit(1);
        } catch (LowBalanceException e) {
            view.printErrorMessage("Insufficient balance. Please remove items from your cart.");
        }
        System.out.println();
        System.out.println("These are our goods today:");
        boolean orderFinished = false;
        try {
            while (!orderFinished) {
                view.printAllFoods(service.getDataStore().getFoods());
                System.out.println();
                Food food = view.selectFood(service.getDataStore().getFoods());
                int pieces = view.readPieces();
                service.updateCart(authenticatedCustomer, food, pieces);
                view.printAddedToCart(food, pieces);
                view.printCart(authenticatedCustomer.getCart());
                System.out.println();
                orderFinished = view.promptOrder();

                if (orderFinished) {
                    try {
                        Order createdOrder = service.createOrder(authenticatedCustomer);

                        if (createdOrder != null) {
                            view.printConfirmedOrder(createdOrder, authenticatedCustomer.getBalance());
                        } else {
                            orderFinished = false;
                        }
                    } catch (LowBalanceException e) {
                        view.printErrorMessage(e.getMessage());
                        orderFinished = false; // Continue the loop if a LowBalanceException is caught
                    }
                }
            }
        } catch (LowBalanceException e) {
            view.printErrorMessage(e.getMessage());
        }
    }
    }


