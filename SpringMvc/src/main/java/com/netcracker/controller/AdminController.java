package com.netcracker.controller;

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
        model.addObject("rub","\u20BD");
        model.setViewName("admin");
        if (allOrders != null && allOrders.size()!=0){
            model.addObject("orders", allOrders);
        }
        if (allUsers != null && allUsers.size()!=0){
            model.addObject("users", allUsers);
        }
        return model;
    }

    @RequestMapping(value = { "/admin/{id}"}, method = RequestMethod.POST)
    public String deleteOrder(@PathVariable BigInteger id, Principal principal) throws IOException {
        Order order = orderService.getOrderById(id);
        if (order != null){
            if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_COURIER")) {
                orderService.changeOrderStatus(id, 803); //created
            }
            if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_USER")) {
                orderService.changeOrderStatus(id, 809); //cancelled
            }
        }
        return "redirect:/my-orders";
    }


}
