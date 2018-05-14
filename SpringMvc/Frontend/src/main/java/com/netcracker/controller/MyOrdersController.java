package com.netcracker.controller;

import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
import com.netcracker.model.Item;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes(value = {"username","basketItems"})
public class MyOrdersController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/my-orders/{pageId}"}, method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public ModelAndView myOrdersPageId(Principal principal, @PathVariable int pageId, Locale locale, HttpSession httpSession){
        ModelAndView model = new ModelAndView();
        List<Order> allOrders = null;
        List<Order> ordersForShow;
        int pageCount;
        try {
            User user  = userService.getByUsername(principal.getName());
            if (user != null){
                model.addObject("user", user);
            }
            if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_COURIER")) {
                model.addObject("role", "ROLE_COURIER");
                allOrders = orderService.getCompletedOrdersByCourier(principal.getName(), locale);
            }
            else if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_USER")) {
                model.addObject("role", "ROLE_USER");
                allOrders = orderService.getOrdersByUsername(principal.getName(), locale);
            }
        }catch (Exception e){
            System.out.println("method myOrders:" + e.getMessage());
        }

        model.addObject("now", LocalDateTime.now());
        model.addObject("chr", ChronoUnit.HOURS);
        model.addObject("start_exp_time", Constant.START_EXPIRATION_TIME);
        model.addObject("page", pageId);

        if (allOrders != null && allOrders.size()!=0){
            Collections.sort(allOrders, Order.COMPARE_BY_DATE);
            ordersForShow = getOrdersRange(Constant.ORDERS_QUANTITY_ON_PAGE * (pageId-1), allOrders);
            model.addObject("orders", ordersForShow);
            pageCount = (int)Math.ceil(((double)allOrders.size())/((double)Constant.ORDERS_QUANTITY_ON_PAGE));
            model.addObject("pageCount", pageCount);
        }
        model.setViewName("my-orders");
        return model;
    }

    private List<Order> getOrdersRange(int startIdx, List<Order> orders){
        List<Order> result = new ArrayList<>();
        int endIdx = startIdx + Constant.ORDERS_QUANTITY_ON_PAGE;
        if (orders.size() < endIdx){
            for (int i = startIdx; i < orders.size(); i++){
                if (orders.get(i) != null){
                    result.add(orders.get(i));
                }
            }
        }else{
            for (int i = startIdx; i < endIdx; i++){
                if (orders.get(i) != null){
                    result.add(orders.get(i));
                }
            }
        }
        return result;
    }


    @RequestMapping(value = { "/my-orders/remove/{id}"}, method = RequestMethod.POST)
    public String deleteOrder(@PathVariable BigInteger id, Principal principal) throws IOException {
        Order order = orderService.getOrderById(id);
        if (order != null){
            if (userService.getByUsername(principal.getName()).getRole().equals("ROLE_USER")) {
                orderService.changeOrderStatus(id, Constant.STATUS_CANCELLED_ENUM_ID); //cancelled
            }
        }
        return "redirect:/my-orders/1";
    }


    @RequestMapping(value = { "/my-orders/markAsExp/{id}"}, method = RequestMethod.POST)
    public String markOrderAsExpired(@PathVariable BigInteger id) throws IOException {
        Order order = orderService.getOrderById(id);
        if (order != null){
            orderService.changeOrderStatus(id, Constant.STATUS_EXPIRED_ENUM_ID); //expired
        }
        return "redirect:/my-orders/1";
    }
}
