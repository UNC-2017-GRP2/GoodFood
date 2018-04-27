package com.netcracker.controller;

import com.google.gson.Gson;
import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class AdminController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = { "/admin"}, method = RequestMethod.GET)
    public ModelAndView adminPanel(Locale locale) throws IOException {
        ModelAndView model = new ModelAndView();
        List<Order> allOrders = orderService.getAllOrders(locale);
        List<User> allUsers = userService.getAllUsers();
       // List<Item> allItems =  itemService.getAllItems(locale);
        model.addObject("now", LocalDateTime.now());
        model.addObject("chr", ChronoUnit.HOURS);


        Map<String, Integer> pieDataMap = new HashMap<>();

        //pieChartData-----------------------------------------------
        List<Item> allItems = new ArrayList<>();
        for (Order o : allOrders)
            allItems.addAll(o.getOrderItems());

        for (Item i : allItems) {
            if (pieDataMap.containsKey(i.getProductName())) {
                pieDataMap.replace(i.getProductName(), pieDataMap.get(i.getProductName()) + i.getProductQuantity());
                continue;
            }
            pieDataMap.put(i.getProductName(), i.getProductQuantity());
        }
        //pieChartData END-------------------------------------------------

        //CoreChart--------------------------------------------
        Map<String, Integer> coreChartDataMap = new HashMap<>();
        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] weekDays = symbols.getShortWeekdays();
        for (String day : weekDays) {
            coreChartDataMap.put(day, 0);
        }

        DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("EEE").withLocale(locale);
        Integer newRevenue;
        for (Order o : allOrders) {
            newRevenue = coreChartDataMap.get(o.getOrderCreationDate().format(weekFormatter)) + o.getOrderCost().intValue();
            coreChartDataMap.replace(o.getOrderCreationDate().format(weekFormatter), newRevenue);
        }
        //CoreChart    END--------------------------------------------


        //Linechart----------------------------------------------
        Map<String, Integer> revenuePerDayMap = new TreeMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM EEE").withLocale(locale);
        LocalDateTime dateTime = LocalDateTime.now();

        int i = 0;
        while (++i < 10) {
            revenuePerDayMap.put(dateTime.format(dateTimeFormatter), 0);
            dateTime = dateTime.minusDays(1);
        }

        for (Order o : allOrders)
            if(o.getOrderCreationDate().isAfter(LocalDateTime.now().minusDays(10))) {
                String s = o.getOrderCreationDate().format(dateTimeFormatter);
                newRevenue = revenuePerDayMap.get(o.getOrderCreationDate().format(dateTimeFormatter)) + o.getOrderCost().intValue();
                revenuePerDayMap.replace(o.getOrderCreationDate().format(dateTimeFormatter), newRevenue);
            }
        //  revenuePerDayMap.

        //LineChartEnd---------------------------------------------------

        //model.addObject("rub","");
        model.setViewName("admin");
        if (allOrders != null){
            model.addObject("orders", allOrders);
        }
        if (allUsers != null){
            model.addObject("users", allUsers);
        }
        if (allItems != null){
            model.addObject("items", allItems);
        }
        if (pieDataMap != null) {
            model.addObject("pieDataMap", pieDataMap);
        }
        if (coreChartDataMap != null) {
            model.addObject("coreChartDataMap", coreChartDataMap);
        }
        if ( revenuePerDayMap != null) {
            model.addObject("revenuePerDayMap", revenuePerDayMap);
        }

        model.addObject("weekDays", weekDays);

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

    @RequestMapping(value = {"/getUserInfo"}, method = RequestMethod.GET)
    public @ResponseBody String getUserInfo(@RequestParam BigInteger userId) throws IOException {
        Gson gson = new Gson();
        String user = gson.toJson(userService.getUserById(userId));
        return user;
    }

    @RequestMapping(value = "/removeUser", method = RequestMethod.GET)
    public @ResponseBody
    void removeUser(@RequestParam BigInteger userId){
        userService.removeUserById(userId);
    }
}
