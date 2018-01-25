package com.victoria.controller;

import com.victoria.model.Item;
import com.victoria.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;

@Controller
@SessionAttributes(value = {"basketItems"})
public class BasketController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public ModelAndView toBasket(@SessionAttribute ArrayList<Item> basketItems){
        ModelAndView model = new ModelAndView();
        model.addObject("rub","\u20BD");
        if(basketItems != null && basketItems.size() != 0){
            BigInteger summa = orderService.totalOrder(basketItems);
            model.addObject("totalOrder", summa);
        }
        model.setViewName("basket");
        return model;
    }


    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(@SessionAttribute ArrayList<Item> basketItems, Principal principal, SessionStatus sessionStatus){
        if(basketItems != null && basketItems.size() != 0){
            orderService.checkout(basketItems,principal.getName());
             sessionStatus.setComplete();
        }
        return "redirect:/my-orders";
    }
}
