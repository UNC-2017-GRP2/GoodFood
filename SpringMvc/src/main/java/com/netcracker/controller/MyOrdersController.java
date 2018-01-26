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

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

@Controller
@SessionAttributes(value = {"username","basketItems"})
public class MyOrdersController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/my-orders"}, method = RequestMethod.GET)
    public ModelAndView myOrdersPage(Principal principal, HttpSession httpSession) throws IOException {
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
            model.addObject("del","Drop the order");
            allOrders = orderService.getAllOrdersByCourier(principal.getName());
        }
        else if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_USER")) {
            model.addObject("del","Delete the order");
            allOrders = orderService.getAllOrdersByUser(principal.getName());
        }
        model.addObject("rub","\u20BD");
        model.setViewName("my-orders");

        if (allOrders != null && allOrders.size()!=0){
            model.addObject("orders", allOrders);
        }
        return model;
    }
    @RequestMapping(value = { "/my-orders/{id}"}, method = RequestMethod.POST)
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
