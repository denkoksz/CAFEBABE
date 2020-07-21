package com.training360.cafebabe.webshop.order.service;

import com.training360.cafebabe.webshop.basket.entities.Basket;
import com.training360.cafebabe.webshop.basket.service.BasketService;
import com.training360.cafebabe.webshop.order.controller.OrderOperationResponse;
import com.training360.cafebabe.webshop.order.entities.Order;
import com.training360.cafebabe.webshop.order.entities.OrderReportByMonthAndStatus;
import com.training360.cafebabe.webshop.order.entities.OrderReportsByProducts;
import com.training360.cafebabe.webshop.order.entities.OrderStatus;
import com.training360.cafebabe.webshop.order.repository.OrderRepository;
import com.training360.cafebabe.webshop.order.repository.UpdateOrderStatusResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private BasketService basketService;

    public OrderService(OrderRepository orderRepository, BasketService basketService) {
        this.orderRepository = orderRepository;
        this.basketService = basketService;
    }

    public int getNumberOfOrders() {
        return orderRepository.getNumberOfOrders();
    }

    public int getNumberOfActiveOrders() {
        return orderRepository.getNumberOfActiveOrders();
    }

    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.getOrdersByUsername(username);
    }

    public List<OrderReportByMonthAndStatus> listOrdersByMonthAndStatus() {
        return orderRepository.listOrdersByMonthAndStatus();
    }

    public List<OrderReportsByProducts> listOrdersByProducts() {
        return orderRepository.listOrdersByProducts();
    }

    public OrderOperationResponse checkOutBasket(String username) {
        Basket myBasket = basketService.getBasket(username);
        if (myBasket.getSum() == 0 && myBasket.getProducts().size() == 0) {
            return new OrderOperationResponse()
                    .setSuccess(false)
                    .setMessage("Empty basket cannot be ordered.");
        }
        if (!orderRepository.createOrder(username, myBasket)) {
            return new OrderOperationResponse()
                    .setSuccess(false)
                    .setMessage("Order was not sent.");
        }
        basketService.deleteBasket(username);
        return new OrderOperationResponse()
                .setSuccess(true)
                .setMessage("Thanks for your order. You can check your order under My Orders");
    }

    public UpdateOrderStatusResponse updateOrderStatus(long id) {
        OrderStatus orderStatus = orderRepository.getOrderStatusById(id);
        if (orderStatus == null) {
            return new UpdateOrderStatusResponse()
                    .setSuccess(false)
                    .setMessage("Order does not exist.");
        }
        if (orderStatus != OrderStatus.ACTIVE) {
            return new UpdateOrderStatusResponse()
                    .setSuccess(false)
                    .setMessage("Only active order status can be changed.");
        }
        return orderRepository.updateOrderStatus(id);
    }


    public List<Order> listAllOrders() {
        return orderRepository.listAllOrders();
    }

    public List<Order> listAllActiveOrders() {
        return orderRepository.listAllActiveOrders();
    }

    public OrderOperationResponse deleteProductFromOrder(Long orderId, Long productId) {
        if (orderRepository.getOrderStatusById(orderId) != OrderStatus.ACTIVE) {
            return new OrderOperationResponse().setSuccess(false).setMessage("You can only delete product from ACTIVE order!");
        }
        if (orderRepository.deleteProductFromOrder(orderId, productId) > 0) {
            if (orderRepository.countProductsPerOrder(orderId) == 0){
                orderRepository.deleteOrder(orderId);
                return new OrderOperationResponse().setSuccess(true).setMessage("Order does not contain any other product. Whole order was deleted!");
            }
            return new OrderOperationResponse().setSuccess(true).setMessage("Product from order successfully deleted!");
        } else {
            return new OrderOperationResponse().setSuccess(false).setMessage("Product does not found to delete!");
        }
        // logikát írni ha teljesen kiürül a rendelés akkor törölni a rendelést is
    }

    public OrderOperationResponse deleteOrder(Long orderID) {
        return orderRepository.deleteOrder(orderID);
    }


}
