Introduction
============

The Food Delivery application first version has been implemented in the Java Basics module. The application should be extended in this exercise (some functions can be deleted). See the functional description in the next chapters for the changes.

Data Model
==========

The first version of the application read orders and order items from order.csv file. This exercise reads also food  and customer details from csv files.

Format of foods.csv
-------------------

    
| Name   | Calories | Description | Price   (EUR) | Category |
|--------|----------|-------------|---------------|----------|
| Fideua | 558      | Fideua      | 15            | MEAL     |

Format of orders.csv
--------------------

      
| Order Id | Customer Id | OrderItem name | OrderItem pieces | OrderItem price | Timestamp   (dd/MM/yyyy HH:mm) | Total price of order   (EUR) |
|----------|-------------|----------------|------------------|-----------------|--------------------------------|------------------------------|
| 1        | 20          | Tortilla       | 2                | 20              | 28/06/2019 23:09               | 36                           |
| 1        | 20          | Gazpacho       | 2                | 16              | 28/06/2019 23:09               | 36                           |

orders.csv is structured in a way that a single order can be composed of multiple rows of (one for each Order Item).

Format of customers.csv
-----------------------

    
| Email address                                    | Password       | Customer Id | Customer name | Account balance   (EUR) |
|--------------------------------------------------|----------------|-------------|---------------|-------------------------|
| [test\_user@epam.com](mailto:test_user@epam.com) | XpQC6C4bfqF4ds | 2           | Test User     | 100                     |

Functionality
=============

The application drives the user through a well defined user flow.

1.  Application data initialization: read food, customer, orders with order items from csv files.
2.  Authenticate the customer.
3.  List all the foods.
4.  The user selects a food and specifies the amount. If a selected food is already in the cart, then selecting that food now and specifying its amount overwrites the old amount in the cart. If the new amount is 0, then the food is removed from the cart.
5.  The user is prompted to continue:
    1.  If the user continues to shop, go back to #3.
    2.  If not, create the order.
6.  Print the order details and thank the user for his/her purchase.

Error handling:

*   For functionality (1), authentication.
    *   If the user enters incorrect credentials, the application writes a message to the console. There is no need to request credentials again, the application quits.
*   For functionality (5b), order creation.
    *   If the price of the cart is higher than the user's balance, the application writes a message to the console, asking the user to remove something from the cart, then continues from step 3.
*   In all other error cases the application can stop running.

Expected result
---------------

    Enter customer email address:test_user@epam.com
    Enter customer password:XpQC6C4bfqF4ds
    Welcome, Test User. Your balance is: 100 EUR.
    
    These are our goods today:
    - Fideua 15 EUR each
    - Paella 13 EUR each
    - Tortilla 10 EUR each
    - Gazpacho 8 EUR each
    - Quesadilla 13 EUR each
    
    Please enter the name of the food you would like to buy or delete from the cart:Churros
    Invalid input.
    Please enter the name of the food you would like to buy or delete from the cart:Fideua
    How many pieces do you want to buy? This input overwrites the old value in the cart, entering zero removes the item completely:2
    Added 2 piece(s) of Fideua to the cart.
    The cart has 30 EUR of foods:
    Fideua 2 piece(s), 30 EUR total
    
    Are you finished with your order? (y/n)n
    - Fideua 15 EUR each
    - Paella 13 EUR each
    - Tortilla 10 EUR each
    - Gazpacho 8 EUR each
    - Quesadilla 13 EUR each
    
    Please enter the name of the food you would like to buy or delete from the cart:Gazpacho
    How many pieces do you want to buy? This input overwrites the old value in the cart, entering zero removes the item completely:1
    Added 1 piece(s) of Gazpacho to the cart.
    The cart has 38 EUR of foods:
    Fideua 2 piece(s), 30 EUR total
    Gazpacho 1 piece(s), 8 EUR total
    
    Are you finished with your order? (y/n)Maybe
    Invalid input.
    Are you finished with your order? (y/n)y
    
    Order (items: [Fideua, Gazpacho], price: 38 EUR, timestamp: 18-01-2023 09:22:21) has been confirmed.
    Your balance is 62 EUR.
    Thank you for your purchase.

Example for low balance handling:

    Enter customer email address:test_user@epam.com
    Enter customer password:XpQC6C4bfqF4ds
    Welcome, Test User. Your balance is: 100 EUR.
    
    These are our goods today:
    - Fideua 15 EUR each
    - Paella 13 EUR each
    - Tortilla 10 EUR each
    - Gazpacho 8 EUR each
    - Quesadilla 13 EUR each
    
    Please enter the name of the food you would like to buy or delete from the cart:Quesadilla
    How many pieces do you want to buy? This input overwrites the old value in the cart, entering zero removes the item completely:10
    Added 10 piece(s) of Quesadilla to the cart.
    The cart has 130 EUR of foods:
    Quesadilla 10 piece(s), 130 EUR total
    Are you finished with your order? (y/n)y
    
    You don't have enough money, your balance is only 100 EUR. We do not empty your cart, please remove some of the items.
    - Fideua 15 EUR each
    - Paella 13 EUR each
    - Tortilla 10 EUR each
    - Gazpacho 8 EUR each
    - Quesadilla 13 EUR each
    
    Please enter the name of the food you would like to buy or delete from the cart:Quesadilla
    How many pieces do you want to buy? This input overwrites the old value in the cart, entering zero removes the item completely:1
    Added 1 piece(s) of Quesadilla to the cart.
    The cart has 13 EUR of foods:
    Quesadilla 1 piece(s), 13 EUR total
    
    Are you finished with your order? (y/n)y
    
    Order (items: [Quesadilla], price: 13 EUR, timestamp: 18-01-2023 09:25:35) has been confirmed.
    Your balance is 87 EUR.
    Thank you for your purchase.

Application Classes
===================

The following class diagrams and summary table (including the ones in the following section) were written primarily with the Java language in mind - when another language is used, follow the appropriate conventions. In case of .NET

*   Namespace and method names should be written in camel case.
*   The IList interface should be used in place of Java's List.
*   Decimal where BigDecimal is specified, DateTime instead of LocalDateTime.
*   System.Exception should be the base class of custom exceptions etc.
*   The tester requires external manipulation of private fields. To be implemented with properties.

Domain Model
------------

![](https://raw.githubusercontent.com/epam-mep-java/exercise-specification-images/main/object-oriented-principles-food-delivery/order-domain.png)

 
| Class     | Description                                                                                                                                                                                                                                                        |
|-----------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| User      | Object used to hold login data and retrieve the corresponding Customer object during authentication.                                                                                                                                                               |
| Customer  | The object representing the user after login. This stores all the information regarding the customer.                                                                                                                                                              |
| Cart      | The current shopping cart of the customer in the application.                                                                                                                                                                                                      |
| Order     | Object holding all the information of a (past) order of a customer.  id of the order is **Long** wrapper class, **not long** primitive; reason: the Order id is null until DataStore generates a new id for it.  In case of .NET, nullable value type can be used. |
| OrderItem | A subunit of both Orders and Carts. An OrderItem holds a Food and the number of pieces of food being ordered.                                                                                                                                                      |
| Food      | An object representing the food that can be purchased in the application.                                                                                                                                                                                          |
| Category  | An enum containing all the categories where Foods belong to.                                                                                                                                                                                                       |

**Notes**: 

*   All domain classes need to have a default no arg constructor. Feel free to add new ways to create these objects.

Service, View and Data
======================

![](https://raw.githubusercontent.com/epam-mep-java/exercise-specification-images/main/object-oriented-principles-food-delivery/order-service.png)

![](https://raw.githubusercontent.com/epam-mep-java/exercise-specification-images/main/object-oriented-principles-food-delivery/order-exceptions.png)

![](https://raw.githubusercontent.com/epam-mep-java/exercise-specification-images/main/object-oriented-principles-food-delivery/order-view.png)

 
| Class                                           | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
|-------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| App                                             | Entry point of the application, this class should control the application flow (e.g. do listing food, adding foods to cart, ordering). This class should use Service and View classes to implement functionality.                                                                                                                                                                                                                                                                           |
|                                                 | **Exception handling:** Handles LowBalanceException, asks for choosing from foods again. Catches AuthenticationException, but makes the program exit.                                                                                                                                                                                                                                                                                                                                       |
| FoodDeliveryService  DefaultFoodDeliveryService | **FoodDeliveryService**: service interface.  **DefaultFoodDeliveryService**: implementation of FoodDeliveryService. Needs a DataStore argument to be passed in its constructor. Contains logic: getting the list of foods, checking user balance, etc.                                                                                                                                                                                                                                      |
|                                                 | **authenticate():** Should throw AuthenticationException if given incorrect credentials.                                                                                                                                                                                                                                                                                                                                                                                                    |
|                                                 | **updateCart():** See chapter 3, step 4 for a an overview. **Price calculation:** The price of the new or modified OrderItem and the new price of the Cart should be calculated. Currently, the price of an OrderItem is the price of the food multiplied by the amount of the food ordered. The total price of a Cart is the sum of the prices of its OrderItems. **Throws:** IllegalArgumentException if pieces is 0 and the given food is not in the cart yet, or if pieces is negative. |
|                                                 | **createOrder():** An Order is created by converting the contents of the cart into an order. Then the cart is emptied. **Price calculation:** The price of the Order and the new price of the Cart should be calculated. The total price of an Order is the sum of the prices of its OrderItems. **Throws:** LowBalanceException if the user's balance is not enough to create the order. IllegalStateException if the cart is empty.                                                       |
| LowBalanceException  AuthenticationException    | Basic unchecked exceptions for domain specific issues.                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| View  CLIView                                   | **View:** interface for user interaction, both input and output.  **CLIView:** View implementation that deals with the keyboard and console.  The date/time format used by this class is: **"DD-MM-YYYY hh:mm:ss"**                                                                                                                                                                                                                                                                         |
| DataStore  FileDataStore                        | **DataStore:** interface that provides methods to access data.  **FileDataStore:** Implementation of the DataStore interface that is responsible to load data from the csv files. Constructor needs the folder name in which the files are loaded from. CSV file names are hard-coded: **customers.csv**, **orders.csv** and **foods.csv**.                                                                                                                                                 |
|                                                 | **createOrder():** creates a new order. Order id generation: take the max value of the existing order ids and increment by one. The new order must be added to the owner customer's orders collection, and the order collection in DataStore. Insert it at the end of the collections. This method should not write to orders.csv, store the new order in memory.                                                                                                                           |

**Notes**: 

*   Reading the input files:
    *   When running the application, users should pass the name of the folder in which the csv files are located as a **command line argument**.
    *   When you create the path for a specific csv file in FileDataStore, concat baseDirPath, separator, and the file name. Use system specific file separator: File.separator (java), Environment.NewLine (.NET).
*   The classes must have the fields and methods as defined in the class diagrams. The UML diagrams only show what is the specification of the homework. You may implement **additional methods (or even classes!)** as you see fit.
*   The `price` field in OrderItems, Orders and Cart might seem unnecessary and redundant, but the price calculation mechanism may change in a later homework assignment. For example, discounts can be introduced based on certain conditions.

Constraints
===========

Restrictions and other criteria:

*   Using services in View class(es) is not allowed.
*   Using View class(es) in services is not allowed.
*   Domain model classes should not contain complex logic.
    *   Creation: you can add constructors, other methods that helps to create the objects, and set up their relations with each other.
    *   Update data: you can add methods to query or update data. E.g. the Cart can have methods to manage its order items.
*   Make sure to properly implement `equals()`  and `toString()` for all classes in package `domain`  (except the enum)
    *   In .NET, the equivalent Equals() and ToString() methods should be overridden.

Technology
==========

*   Use Java version 14
*   You may use Lombok.
    *   Be careful when using Lombok annotations for class hierarchies - use callSuper attribute.
    *   Circular dependencies can cause generated methods to fall into infinite recursion - use exclusion attribute.

Acceptance
==========

*   Functionality is implemented as defined in the relevant chapter.
*   Object-Oriented principles are followed.

  

**Document Last Modification: 2023.03.27. 15:16**