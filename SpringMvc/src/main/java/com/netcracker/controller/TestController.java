package com.netcracker.controller;

import com.netcracker.config.Constant;
import com.netcracker.model.Item;
import com.netcracker.model.Order;
import com.netcracker.model.User;
import com.netcracker.service.ItemService;
import com.netcracker.service.OrderService;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class TestController {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    /*@RequestMapping(value = {"/test"}, method = RequestMethod.GET)
    public ModelAndView homeValue(ModelAndView model, @RequestParam(value = "value", required = false) String value, Locale
            locale, Principal principal, HttpSession httpSession) throws IOException {
        if (principal != null) {
            User user = userService.getByUsername(principal.getName());
            if (httpSession.getAttribute("username") == null) {
                httpSession.setAttribute("username", principal.getName());
            }
            if (!user.getRole().equals("ROLE_COURIER")) {
                if (httpSession.getAttribute("basketItems") == null) {
                    httpSession.setAttribute("basketItems", new ArrayList<Item>());
                }
            }
            if (httpSession.getAttribute("userAddresses") == null) {
                httpSession.setAttribute("userAddresses", user.getAddresses());
            }
            if (httpSession.getAttribute("userPhone") == null) {
                httpSession.setAttribute("userPhone", user.getPhoneNumber());
            }
            if (user.getRole().equals("ROLE_COURIER")) {
                model.setViewName("redirect:/my-orders");
                return model;
            }
        }

        List<Item> currentItems = itemService.getItemsByCategory(value, locale);
        if (value == null) {
            model.addObject("value", "Pizza");
        } else {
            model.addObject("value", value);
        }
        if (currentItems == null) {
            currentItems = itemService.getItemsByCategory("Pizza", locale);

        }
        model.addObject("items", currentItems);

        //model.addObject("notification", null);
        model.setViewName("home");
        return model;
    }

    @RequestMapping(value = {"/test1"}, method = RequestMethod.GET)
    public ModelAndView myOrd(Principal principal) throws IOException {

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
        model.setViewName("my-orders-old");

        if (allOrders != null && allOrders.size()!=0){
            model.addObject("orders", allOrders);
        }
        return model;
    }*/
}


