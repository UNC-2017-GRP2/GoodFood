package com.netcracker.controller;

import com.netcracker.model.Order;
import com.netcracker.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

@Controller
@SessionAttributes(value = {"username"})
public class FreeOrdersController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = { "/free-orders"}, method = RequestMethod.GET)
    public ModelAndView freeOrdersPage() throws IOException {
        ModelAndView model = new ModelAndView();
        model.addObject("add","Take the order");
        model.addObject("rub","\u20BD");
        model.setViewName("free-orders");

        List<Order> allOrders = orderService.getAllFreeOrders();
        if (allOrders != null && allOrders.size()!=0){
            model.addObject("orders", allOrders);
        }
        return model;
    }
    @RequestMapping(value = { "/free-orders/{id}"}, method = RequestMethod.POST)
    public String takeOrder(@PathVariable BigInteger id, Principal principal) throws IOException {
        Order order = orderService.getOrderById(id);
        if (order != null){
            orderService.setCourier(id, principal.getName());
        }
        return "redirect:/free-orders";
    }


}
