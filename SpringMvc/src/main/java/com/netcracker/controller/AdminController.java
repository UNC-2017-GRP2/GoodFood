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

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/admin"}, method = RequestMethod.GET)
    public ModelAndView adminPanel() throws IOException {
        ModelAndView model = new ModelAndView();
        List<Order> allOrders = orderService.getAllOrders();
        List<User> allUsers = userService.getAllUsers();
        model.addObject("now", LocalDateTime.now());
        model.addObject("chr", ChronoUnit.HOURS);
        //model.addObject("rub","");
        model.setViewName("admin");
        if (allOrders != null && allOrders.size()!=0){
            model.addObject("orders", allOrders);
        }
        if (allUsers != null && allUsers.size()!=0){
            model.addObject("users", allUsers);
        }
        return model;
    }

    @RequestMapping(value = { "/admin/actualize"}, method = RequestMethod.POST)
    public String markOrderAsExpired() throws IOException {
        List<Order> allOrders = orderService.getAllOrders();
        for (Order order : allOrders) {
            if (order.getOrderCreationDate().until(LocalDateTime.now(), ChronoUnit.HOURS) > Constant.END_EXPIRATION_TIME) {
                orderService.changeOrderStatus(order.getOrderId(), Constant.STATUS_EXPIRED_ENUM_ID);
            }
        }
        return "redirect:/admin";
    }
}
