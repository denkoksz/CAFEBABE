package com.training360.cafebabe.webshop.dashboard.controller;

import com.training360.cafebabe.webshop.order.service.OrderService;
import com.training360.cafebabe.webshop.product.service.ProductService;
import com.training360.cafebabe.webshop.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
    private ProductService productService;
    private UserService userService;
    private OrderService orderService;

    public DashboardController(ProductService productService, UserService userService, OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @RequestMapping("/api/dashboard")
    public DashboardResponse getDashboardValues(Authentication authentication) {
        if (authentication != null && isAdmin(authentication)) {

        int numberOfProducts = productService.getNumberOfProducts();
        int numberOfActiveProducts = productService.getNumberOfActiveProducts();
        int numberOfUsers = userService.getNumberOfUsers();
        int numberOfOrders = orderService.getNumberOfOrders();
        int numberOfActiveOrders = orderService.getNumberOfActiveOrders();

        return new DashboardResponse()
                .setNumberOfProducts(numberOfProducts)
                .setNumberOfActiveProducts(numberOfActiveProducts)
                .setNumberOfUsers(numberOfUsers)
                .setNumberOfOrders(numberOfOrders)
                .setNumberOfActiveOrders(numberOfActiveOrders);
        }
        return new DashboardResponse();
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
