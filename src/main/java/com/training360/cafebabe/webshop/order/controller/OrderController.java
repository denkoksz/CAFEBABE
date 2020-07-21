package com.training360.cafebabe.webshop.order.controller;

import com.training360.cafebabe.webshop.order.entities.Order;
import com.training360.cafebabe.webshop.order.entities.OrderReportByMonthAndStatus;
import com.training360.cafebabe.webshop.order.entities.OrderReportsByProducts;
import com.training360.cafebabe.webshop.order.repository.UpdateOrderStatusResponse;
import com.training360.cafebabe.webshop.order.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/api/myorders", method = RequestMethod.GET)
    public List<Order> getMyOrders(Authentication authentication) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println(userDetails.getUsername() + " " + userDetails.getAuthorities());
            return orderService.getOrdersByUsername(userDetails.getUsername());
        }
        return Collections.emptyList();
    }

    @RequestMapping(value = "/api/reports/orders", method = RequestMethod.GET)
    public List<OrderReportByMonthAndStatus> listOrdersByMonthAndStatus(Authentication authentication) {
        if (authentication != null && isAdmin(authentication)) {
            return orderService.listOrdersByMonthAndStatus();
        }
        return Collections.emptyList();
    }

    @RequestMapping(value = "/api/reports/products", method = RequestMethod.GET)
    public List<OrderReportsByProducts> listOrdersByProducts(Authentication authentication) {
        if (authentication != null && isAdmin(authentication)) {
            return orderService.listOrdersByProducts();
        }
        return Collections.emptyList();

    }

    @RequestMapping(value = "/api/basket/checkout", method = RequestMethod.POST)
    public OrderOperationResponse checkOutBasket(Authentication authentication) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println(userDetails.getUsername() + " " + userDetails.getAuthorities());
            return orderService.checkOutBasket(userDetails.getUsername());
        }
        return new OrderOperationResponse()
                .setSuccess(false)
                .setMessage("Login needed for ordering.");
    }

    @RequestMapping(value = "/api/orders/{id}/status", method = RequestMethod.POST)
    public UpdateOrderStatusResponse updateOrderStatus(@PathVariable long id, Authentication authentication){
        if (authentication != null && isAdmin(authentication)) {
            return orderService.updateOrderStatus(id);
        }
        return new UpdateOrderStatusResponse()
                .setSuccess(false)
                .setMessage("You do not have privileges to change order status");
    }

    @RequestMapping(value = "/api/orders", method = RequestMethod.GET)
    public List<Order> listAllOrders(Authentication authentication) {
        if (authentication != null && isAdmin(authentication)) {
            return orderService.listAllOrders();
        }
        return Collections.emptyList();
    }

    @RequestMapping(value = "/api/orders/active", method = RequestMethod.GET)
    public List<Order> listAllActiveOrders(Authentication authentication) {
        if (authentication != null && isAdmin(authentication)) {
            return orderService.listAllActiveOrders();
        }
        return Collections.emptyList();
    }

    @RequestMapping(value = "/api/orders/{orderId}/{productId}", method = RequestMethod.DELETE)
    public OrderOperationResponse deleteProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId, Authentication authentication){
        if (authentication != null && isAdmin(authentication)) {
            return orderService.deleteProductFromOrder(orderId, productId);
        }
        return new OrderOperationResponse().setSuccess(false).setMessage("You dont have permission to delete!");
    }

    @RequestMapping(value = "/api/orders/{orderid}", method = RequestMethod.DELETE)
    public OrderOperationResponse deleteOrder(@PathVariable Long orderid, Authentication authentication){
        if (authentication != null && isAdmin(authentication)) {
            return orderService.deleteOrder(orderid);
        }
       return new OrderOperationResponse().setSuccess(false).setMessage("You dont have permission to delete!");
    }

    private boolean isAdmin(Authentication authentication) {
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
