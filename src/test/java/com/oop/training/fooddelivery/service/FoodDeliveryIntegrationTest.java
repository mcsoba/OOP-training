package com.oop.training.fooddelivery.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.oop.training.fooddelivery.data.FileDataStore;
import com.oop.training.fooddelivery.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FoodDeliveryIntegrationTest {
    private static final String INPUT_FOLDER_PATH = "input";
    private static final long NEXT_ORDER_ID = 41L;

    private DefaultFoodDeliveryService underTest;
    private FileDataStore fileDataStore;

    @BeforeEach
    public void setup() {
        fileDataStore = new FileDataStore(INPUT_FOLDER_PATH);
        fileDataStore.init();
        assertAll("Some of the collections in FileDataStore are not initialized correctly.",
                () -> assertFalse(fileDataStore.getCustomers().isEmpty(),
                        "Customers collection in FileDataStore is empty after FileDataStore.init() call"),
                () -> assertFalse(fileDataStore.getOrders().isEmpty(),
                        "Orders collection in FileDataStore is empty after FileDataStore.init() call")
        );

        underTest = new DefaultFoodDeliveryService(fileDataStore);
    }

    @Test
    @DisplayName("authenticate should return the correct customer when given correct credentials")
    public void testAuthenticateShouldReturnTheCorrectCustomerWhenGivenCorrectCredentials() {
        // GIVEN
        User quickLoginUser = createQuickLoginUser();
        long expectedId = 1;

        // WHEN
        Customer customer = underTest.authenticate(quickLoginUser);

        // THEN
        assertEquals(expectedId, customer.getId(), "Wrong customer customer returned, IDs do not match.");
    }

    @Test
    @DisplayName("authenticate should throw AuthenticationException when called with incorrect credentials")
    public void testAuthenticateShouldThrowAuthenticationExceptionWhenGivenIncorrectCredentials() {
        // GIVEN
        User user = new User();
        user.setEmail("a");
        user.setPassword("incorrect");

        // WHEN

        // THEN
        assertThrows(AuthenticationException.class, () -> underTest.authenticate(user),
                "authenticate did not throw AuthenticationException when called with"
                        + " email: 'a' and password: 'incorrect'.");
    }

    @Test
    @DisplayName("createOrder should create an order with correct values"
            + " and add it to the customer and the datastore")
    public void testCreateOrderShouldCreateOrderWithCorrectValuesAndAddItToCustomerAndDataStore() {
        // GIVEN
        Customer customer = createCustomerWithCart(createOneItemCartWithOneFideua());

        Order expected = createOrder(customer, NEXT_ORDER_ID);

        // WHEN
        Order actual = underTest.createOrder(customer);

        // THEN
        assertThat(expected)
                .usingRecursiveComparison()
                .ignoringFields("timestampCreated")
                .withFailMessage("The created order has incorrect values!"
                        + " Expected: " + expected + ", actual: " + actual + "."
                        + " (timestampCreated field is ignored.)")
                .isEqualTo(actual);

        assertAll(
                () -> assertNewOrderAddedToCustomer(customer),
                this::assertNewOrderAddedToDataStore
        );
    }

    private void assertNewOrderAddedToCustomer(Customer customer) {
        int lastOrderIndexInCustomer = customer.getOrders().size() - 1;
        long idOfLastOrderInCustomer = customer.getOrders().get(lastOrderIndexInCustomer).getOrderId();
        assertEquals(NEXT_ORDER_ID, idOfLastOrderInCustomer,
                "The created order wasn't added to the Customer's order collection."
                        + " The ID of the last Order is incorrect.");
    }

    private void assertNewOrderAddedToDataStore() {
        int lastOrderIndexInDataStore = fileDataStore.getOrders().size() - 1;
        long idOfLastOrderInDataStore = fileDataStore.getOrders().get(lastOrderIndexInDataStore).getOrderId();
        assertEquals(NEXT_ORDER_ID, idOfLastOrderInDataStore,
                "The created order wasn't added to the DataStore. The ID of the last Order is incorrect.");
    }

    private User createQuickLoginUser() {
        User quickLoginUser = new User();
        quickLoginUser.setEmail("a");
        quickLoginUser.setPassword("a");

        return quickLoginUser;
    }

    private Customer createCustomerWithCart(Cart cart) {
        Customer customer = new Customer();
        customer.setName("Test customer");
        customer.setBalance(new BigDecimal(100));
        customer.setCart(cart);

        return customer;
    }

    private Cart createOneItemCartWithOneFideua() {
        BigDecimal price = new BigDecimal(15);
        OrderItem orderItemWithOnePiece = createOrderItemWithFideua(1, price);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItemWithOnePiece);

        return createCart(orderItems, price);
    }

    private OrderItem createOrderItemWithFideua(int pieces, BigDecimal price) {
        OrderItem testOrderItem = new OrderItem();
        testOrderItem.setFood(createFideua());
        testOrderItem.setPieces(pieces);
        testOrderItem.setPrice(price);

        return testOrderItem;
    }

    private Cart createCart(List<OrderItem> orderItemList, BigDecimal price) {
        Cart cartWithOneFood = new Cart();
        cartWithOneFood.setOrderItems(orderItemList);
        cartWithOneFood.setPrice(price);

        return cartWithOneFood;
    }

    private Food createFideua() {
        Food fideua = new Food();
        fideua.setName("Fideua");
        fideua.setCalorie(new BigDecimal(558));
        fideua.setDescription("Fideua");
        fideua.setPrice(new BigDecimal(15));
        fideua.setCategory(Category.MEAL);

        return fideua;
    }

    private Order createOrder(Customer customer, Long id) {
        var order = new Order();
        order.setOrderId(id);
        order.setCustomerId(customer.getId());
        order.setPrice(customer.getCart().getPrice());
        order.setTimestampCreated(LocalDateTime.now());
        order.setOrderItems(customer.getCart().getOrderItems());

        return order;
    }
}
