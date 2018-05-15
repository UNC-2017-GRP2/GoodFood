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
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Controller
public class CurrentOrdersController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = {"/current-orders"}, method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public ModelAndView currentOrders(ModelAndView model, Principal principal, Locale locale) throws IOException {

        List<Order> allOrders = null;
        try {
            User user = userService.getByUsername(principal.getName());
            if (user != null) {
                model.addObject("user", user);
            }
        } catch (Exception e) {
            System.out.println("method currentOrdersPage:" + e.getMessage());
        }
        model.addObject("role", "ROLE_COURIER");
        allOrders = orderService.getNotCompletedOrdersByCourier(principal.getName(), locale);
        model.addObject("now", LocalDateTime.now());
        model.addObject("chr", ChronoUnit.HOURS);
        model.addObject("start_exp_time", Constant.START_EXPIRATION_TIME);
        //model.addObject("rub","\u20BD");
        model.setViewName("current-orders");

        if (allOrders != null && allOrders.size() != 0) {
            Collections.sort(allOrders, Order.COMPARE_BY_DATE);
            model.addObject("orders", allOrders);
        }
        return model;
    }

    @RequestMapping(value = { "/current-orders/remove/{id}"}, method = RequestMethod.POST)
    public String deleteOrder(@PathVariable BigInteger id, Principal principal) throws IOException {
        Order order = orderService.getOrderById(id);
        if (order != null){
            if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_COURIER")) {
                orderService.changeOrderStatus(id, Constant.STATUS_CREATED_ENUM_ID); //created
            }
        }
        return "redirect:/current-orders";
    }

    @RequestMapping(value = { "/current-orders/markAsDeliv/{id}"}, method = RequestMethod.POST)
    public String markOrderAsDelivered(@PathVariable BigInteger id) throws IOException {
        Order order = orderService.getOrderById(id);
        if (order != null){
            orderService.updateOrderPaid(id,1);
            orderService.changeOrderStatus(id, Constant.STATUS_DELIVERED_ENUM_ID); //delivered
        }
        return "redirect:/current-orders";
    }
}
