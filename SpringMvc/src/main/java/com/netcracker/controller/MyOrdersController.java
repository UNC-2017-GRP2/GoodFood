package com.netcracker.controller;

import com.netcracker.config.Constant;
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
            model.addObject("nullParameter", "Отсутствует");
        }catch (Exception e){
            System.out.println("method homePage:" + e.getMessage());
        }
        if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_COURIER")) {
            model.addObject("remove","Drop the order");
            model.addObject("role", "ROLE_COURIER");
            allOrders = orderService.getAllOrdersByCourier(principal.getName());
        }
        else if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_USER")) {
            model.addObject("remove","Delete the order");
            model.addObject("role", "ROLE_USER");
            allOrders = orderService.getOrdersByUsername(principal.getName());
        }
        model.addObject("rub","\u20BD");
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
            if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_COURIER")) {
                orderService.changeOrderStatus(id, Constant.STATUS_CREATED_ENUM_ID); //created
            }
            if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_USER")) {
                orderService.changeOrderStatus(id, Constant.STATUS_CANCELLED_ENUM_ID); //cancelled
            }
        }
        return "redirect:/my-orders";
    }

    @RequestMapping(value = { "/my-orders/markAsDeliv/{id}"}, method = RequestMethod.POST)
    public String markOrderAsDelivered(@PathVariable BigInteger id) throws IOException {
        Order order = orderService.getOrderById(id);
        if (order != null){
                orderService.changeOrderStatus(id, Constant.STATUS_DELIVERED_ENUM_ID); //delivered
        }
        return "redirect:/my-orders";
    }


}
