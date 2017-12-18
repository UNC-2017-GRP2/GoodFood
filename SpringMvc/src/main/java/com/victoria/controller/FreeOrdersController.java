package com.victoria.controller;

import com.victoria.model.Item;
import com.victoria.model.Order;
import com.victoria.service.ItemService;
import com.victoria.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
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
    public String takeOrder(@PathVariable long id,Principal principal) throws IOException {
        Order order = orderService.getOrderById(id);
        if (order != null){
            orderService.setCourier(id, principal.getName());
        }
        return "redirect:/free-orders";
    }


}
