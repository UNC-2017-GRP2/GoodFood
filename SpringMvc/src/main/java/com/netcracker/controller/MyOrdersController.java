package com.netcracker.controller;

import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
import com.netcracker.model.Order;
import com.netcracker.model.User;
import com.netcracker.service.OrderService;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class MyOrdersController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/my-orders"}, method = RequestMethod.GET)
    public ModelAndView myOrdersPage(Principal principal) throws IOException {
        ModelAndView model = new ModelAndView();
        List<Order> allOrders = null;
        try {
            User user  = userService.getByUsername(principal.getName());
            if (user != null){
                model.addObject("user", user);
            }
        }catch (Exception e){
            System.out.println("method homePage:" + e.getMessage());
        }
        if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_COURIER")) {
            model.addObject("role", "ROLE_COURIER");
            allOrders = orderService.getCompletedOrdersByCourier(principal.getName());
        }
        else if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_USER")) {
            model.addObject("role", "ROLE_USER");
            allOrders = orderService.getOrdersByUsername(principal.getName());

        }
        model.addObject("now", LocalDateTime.now());
        model.addObject("chr", ChronoUnit.HOURS);
        model.addObject("start_exp_time", Constant.START_EXPIRATION_TIME);
        model.setViewName("my-orders");

        if (allOrders != null && allOrders.size()!=0){
            model.addObject("orders", allOrders);
        }
        return model;
    }

    @RequestMapping(value = { "/my-orders/remove/{id}"}, method = RequestMethod.POST)
    public String deleteOrder(@PathVariable BigInteger id, Principal principal) throws IOException {
        Order order = orderService.getOrderById(id);
        if (order != null){
            if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_USER")) {
                orderService.changeOrderStatus(id, Constant.STATUS_CANCELLED_ENUM_ID); //cancelled
            }
        }
        return "redirect:/my-orders";
    }


    @RequestMapping(value = { "/my-orders/markAsExp/{id}"}, method = RequestMethod.POST)
    public String markOrderAsExpired(@PathVariable BigInteger id) throws IOException {
        Order order = orderService.getOrderById(id);
        if (order != null){
            orderService.changeOrderStatus(id, Constant.STATUS_EXPIRED_ENUM_ID); //expired
        }
        return "redirect:/my-orders";
    }
}
