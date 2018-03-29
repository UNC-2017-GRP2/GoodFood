package com.netcracker.controller;

import com.netcracker.config.Constant;
import com.netcracker.model.Order;
import com.netcracker.model.User;
import com.netcracker.service.OrderService;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

@Controller
public class AdminController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/admin"}, method = RequestMethod.GET)
    public ModelAndView adminPanel(Locale locale) throws IOException {
        ModelAndView model = new ModelAndView();
        List<Order> allOrders = orderService.getAllOrders(locale);
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

    @RequestMapping(value = { "/admin/actualize"}, method = RequestMethod.GET)
    public String markOrderAsExpired(Locale locale) throws IOException {
        List<Order> allOrders = orderService.getAllOrders(locale);
        for (Order order : allOrders) {
            if (order.getOrderCreationDate().until(LocalDateTime.now(), ChronoUnit.HOURS) > Constant.END_EXPIRATION_TIME) {
                orderService.changeOrderStatus(order.getOrderId(), Constant.STATUS_EXPIRED_ENUM_ID);
            }
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/changeRole", method = RequestMethod.GET)
    public @ResponseBody
    String changeRole(@RequestParam BigInteger userId, @RequestParam String role){
        userService.changeRole(userId, role);
        return "redirect:/admin";
    }
}
