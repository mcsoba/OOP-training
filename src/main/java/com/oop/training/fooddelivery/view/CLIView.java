package com.epam.training.fooddelivery.view;

import com.epam.training.fooddelivery.domain.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class CLIView implements View{
    private final Scanner scanner = new Scanner(System.in);
    public CLIView() {

    }


    @Override
    public User readCredentials() {
        System.out.println("Enter customer email address:");
        String email = scanner.nextLine();
        System.out.println("Enter customer password:");
        String password = scanner.nextLine();
        return new User(email, password);
    }

    @Override
    public void printWelcomeMessage(Customer customer) {
        System.out.println("Welcome, " + customer.getName() + ". Your balance is: " + customer.getBalance() + " EUR.");
    }

    @Override
    public void printAllFoods(List<Food> foods) {
        for (Food food : foods) {
            System.out.println("- " + food.getName() + " " + food.getPrice() + " EUR each");
        }
    }

    @Override
    public Food selectFood(List<Food> foods) {
        boolean validInput = false;
        Food selectedFood = null;
        while (!validInput) {
            System.out.println("Please enter the name of the food you would like to buy or delete from the cart:");
            String enteredFoodName = scanner.nextLine();
            for (Food food : foods) {
                if (food.getName().equalsIgnoreCase(enteredFoodName)) {
                    selectedFood = food;
                    validInput = true;  // Set the flag to exit the loop
                    break;  // Exit the for loop since we found a match
                }
            }

            if (!validInput) {
                System.out.println("Invalid input.");
            }
        }
        return selectedFood;
        }
    @Override
    public int readPieces() {
        System.out.println("How many pieces do you want to buy? This input overwrites the old value in the cart, entering zero removes the item completely:");
        return scanner.nextInt();
    }

    @Override
    public void printAddedToCart(Food food, int pieces) {
        System.out.println("Added " + pieces + " piece(s) of " + food.getName() + " to the cart.");
    }

    @Override
    public void printCart(Cart cart) {
        System.out.println("The cart has " + cart.getPrice() + " EUR of foods:");
        cart.getOrderItems().forEach(orderItem -> {
            Food food = orderItem.getFood();
            int pieces = orderItem.getPieces();
            BigDecimal totalPrice = food.getPrice().multiply(BigDecimal.valueOf(pieces));
            System.out.println(food.getName() + " " + pieces + " piece(s), " + totalPrice + " EUR total");
        });
    }

    @Override
    public void printConfirmedOrder(Order order, BigDecimal balance) {
        System.out.println(order.toString() + " has been confirmed.");
        System.out.println("Your balance is " + balance + " EUR.");
        System.out.println("Thank you for your purchase.");

    }

    @Override
    public boolean promptOrder() {
        scanner.nextLine();
        while (true) {
            System.out.println("Are you finished with your order? (y/n)");
            String userInput = scanner.nextLine().toLowerCase();
            if (userInput.equals("y")) {
                return true;
            } else if (userInput.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input.");
            }

        }
    }

    @Override
    public void printErrorMessage(String message) {
        System.out.println(message);
    }
}
