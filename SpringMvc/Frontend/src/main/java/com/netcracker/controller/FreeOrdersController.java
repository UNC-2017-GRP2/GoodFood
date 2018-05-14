package com.netcracker.controller;

import com.netcracker.model.Address;
import com.netcracker.model.Order;
import com.netcracker.service.OrderService;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes(value = {"username"})
public class FreeOrdersController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/free-orders"}, method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public ModelAndView freeOrdersPage(Locale locale) throws IOException {
        ModelAndView model = new ModelAndView();
        model.addObject("now", LocalDateTime.now());
        model.addObject("chr", ChronoUnit.HOURS);
        model.setViewName("free-orders");

        List<Order> allOrders = orderService.getAllFreeOrders(locale);
        if (allOrders != null && allOrders.size()!=0){
            Collections.sort(allOrders, Order.COMPARE_BY_DATE);
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

    @RequestMapping(value = "/getUsername", method = RequestMethod.GET)
    public @ResponseBody String getUsername(@RequestParam String userId){
        String fio = (userService.getUserById(new BigInteger(userId))!=null)?userService.getUserById(new BigInteger(userId)).getFio():null;
        return fio;
    }
}
