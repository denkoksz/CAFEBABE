package com.training360.cafebabe.webshop.order.controller;

import com.training360.cafebabe.webshop.basket.repository.BasketRepository;
import com.training360.cafebabe.webshop.order.entities.FakeAuthentication;
import com.training360.cafebabe.webshop.order.entities.Order;
import com.training360.cafebabe.webshop.order.entities.OrderStatus;
import com.training360.cafebabe.webshop.order.repository.UpdateOrderStatusResponse;
import com.training360.cafebabe.webshop.order.service.OrderService;
import com.training360.cafebabe.webshop.product.entities.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("classpath:/cleanDBforOrdersTesting.sql")
public class OrderControllerIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void getMyOrdersShouldReturnEmptyListIfNotAuthenticated() {
        //When
        List<Order> result = new OrderController(orderService).getMyOrders(null);

        //Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void getMyOrdersShouldReturnEmptyListIfIsAuthenticatedButDoNotHaveAnyOrders() {
        //Given
        String expectedUsername = "testuserWithoutOrder";
        Authentication authentication = new FakeAuthentication(expectedUsername, "ROLE_USER");

        //When
        List<Order> result = new OrderController(orderService).getMyOrders(authentication);

        //Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }


    @Test
    public void getMyOrdersShouldReturnOrderedListIfIsAuthenticatedAndHaveAnyOrders() {
        //Given
        String expectedUsername = "testuser1";
        Authentication authentication = new FakeAuthentication(expectedUsername, "ROLE_USER");

        //When
        List<Order> result = new OrderController(orderService).getMyOrders(authentication);

        //Then
        assertNotNull(result);
        assertEquals(6, result.size());
        assertEquals(11L, result.get(0).getId().longValue());
        assertEquals(9L, result.get(1).getId().longValue());
        assertEquals(4L, result.get(2).getId().longValue());
        assertEquals(3L, result.get(3).getId().longValue());
        assertEquals(2L, result.get(4).getId().longValue());
        assertEquals(1L, result.get(5).getId().longValue());

        for (int index = 0; index < 6; index++) {
            if (index < 5) {
                assertTrue(result.get(index).getDateTime().isAfter(result.get(index + 1).getDateTime()));
            }
            assertTrue(result.get(index).getProducts().size() > 0);
        }
    }

    @Test
    public void checkOutBasketShouldReturnErrorMessageIfNotAuthenticated() {
        //When
        OrderOperationResponse result = new OrderController(orderService).checkOutBasket(null);

        //Then
        assertNotNull(result);
        assertEquals(false, result.isSuccess());
        assertEquals("Login needed for ordering.", result.getMessage());
    }

    @Test
    public void checkOutBasketShouldReturnErrorIfBasketIsEmpty() {
        //Given
        String expectedUsername = "testuserWithoutOrder";
        Authentication authentication = new FakeAuthentication(expectedUsername, "ROLE_USER");

        //When
        OrderOperationResponse result = new OrderController(orderService).checkOutBasket(authentication);

        //Then
        assertNotNull(result);
        assertEquals(false, result.isSuccess());
        assertEquals("Empty basket cannot be ordered.", result.getMessage());
    }

    @Autowired
    private BasketRepository basketRepository;

    @Test
    public void checkOutBasketShouldRemoveAllItemsFromBasket() {
        //Given
        String expectedUsername = "testuser1";
        Authentication authentication = new FakeAuthentication(expectedUsername, "ROLE_USER");
        List<Product> originalBasket = basketRepository.getBasket(expectedUsername);

        //When
        OrderOperationResponse result = new OrderController(orderService).checkOutBasket(authentication);

        //Then
        assertNotNull(result);
        assertEquals(true, result.isSuccess());
        assertEquals("Thanks for your order. You can check your order under My Orders", result.getMessage());
        assertEquals(3, originalBasket.size());
        assertEquals(0, basketRepository.getBasket(expectedUsername).size());
    }

    @Test
    public void checkOutBasketShouldResultInANewOrder() {
        //Given
        String expectedUsername = "testuser1";
        Authentication authentication = new FakeAuthentication(expectedUsername, "ROLE_USER");
        OrderController orderController = new OrderController(orderService);
        int ordersBeforeSize = orderController.getMyOrders(authentication).size();

        //When
        OrderOperationResponse result = orderController.checkOutBasket(authentication);

        //Then
        assertNotNull(result);
        assertEquals(true, result.isSuccess());
        assertEquals("Thanks for your order. You can check your order under My Orders", result.getMessage());
        List<Order> ordersAfter = orderController.getMyOrders(authentication);
        assertEquals(6, ordersBeforeSize);
        assertEquals(7, ordersAfter.size());
        Order newOrder = ordersAfter.get(0);
        assertEquals(expectedUsername, newOrder.getUserName());
        assertEquals(OrderStatus.ACTIVE, newOrder.getStatus());
        assertEquals(9000, newOrder.getSum());
        assertEquals(3, newOrder.getProducts().size());
    }

    @Test
    public void updateOrderStatusShouldReturnErrorMessageIfNotAuthenticated() {
        //When
        UpdateOrderStatusResponse result = new OrderController(orderService).updateOrderStatus(1, null);

        //Then
        assertNotNull(result);
        assertEquals(false, result.isSuccess());
        assertEquals("You do not have privileges to change order status", result.getMessage());
    }

    @Test
    public void updateOrderStatusShouldReturnErrorMessageIfNotAdmin() {
        //Given
        String expectedUsername = "testuser1";
        Authentication authentication = new FakeAuthentication(expectedUsername, "ROLE_USER");

        //When
        UpdateOrderStatusResponse result = new OrderController(orderService).updateOrderStatus(1, authentication);

        //Then
        assertNotNull(result);
        assertEquals(false, result.isSuccess());
        assertEquals("You do not have privileges to change order status", result.getMessage());
    }

    @Test
    public void updateOrderStatusShouldReturnErrorMessageIfAdminChangesAnOrderWithNotActiveStatus() {
        //Given
        String expectedUsername = "admin";
        Authentication authentication = new FakeAuthentication(expectedUsername, "ROLE_ADMIN");

        //When
        UpdateOrderStatusResponse result = new OrderController(orderService).updateOrderStatus(5, authentication);

        //Then
        assertNotNull(result);
        assertEquals(false, result.isSuccess());
        assertEquals("Only active order status can be changed.", result.getMessage());
    }

    @Test
    public void updateOrderStatusShouldChangeOrderStatusToFulfilledIfAdminChangesAnOrderWithActiveStatus() {
        //Given
        String expectedUsername = "admin";
        Authentication authentication = new FakeAuthentication(expectedUsername, "ROLE_ADMIN");

        //When
        UpdateOrderStatusResponse result = new OrderController(orderService).updateOrderStatus(6, authentication);

        //Then
        assertNotNull(result);
        assertEquals(true, result.isSuccess());
        assertEquals("Order status was successfully changed to FULFILLED", result.getMessage());
    }

    @Test
    public void deleteOrderFromTableSuccess(){
        //Given
        String expectedUsername = "admin";
        Authentication authentication = new FakeAuthentication(expectedUsername, "ROLE_ADMIN");

        //When
        OrderOperationResponse result = new OrderController(orderService).deleteOrder(2L, authentication);
        assertNotNull(result);
        assertEquals(true, result.isSuccess());
        assertEquals("Order table is successfully deleted!", result.getMessage());
    }
}
