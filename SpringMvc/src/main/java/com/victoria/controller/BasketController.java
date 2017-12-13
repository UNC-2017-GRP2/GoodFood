package com.victoria.controller;

import com.victoria.model.Item;
import com.victoria.repository.OrderRepository;
import com.victoria.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;

@Controller
@SessionAttributes(value = {"username","basketItems"})
public class BasketController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public ModelAndView toBasket(@SessionAttribute ArrayList<Item> basketItems) throws IOException {
        ModelAndView model = new ModelAndView(/*"session_basket"*/);
        //model.addObject(basketItems);
        model.addObject("rub","\u20BD");
        if(basketItems != null && basketItems.size() != 0){
            BigDecimal summa = orderService.totalOrder(basketItems);
            model.addObject("totalOrder", summa);
        }
        model.setViewName("basket");
        return model;
    }


    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(@SessionAttribute ArrayList<Item> basketItems, Principal principal, HttpSession httpSession, SessionStatus sessionStatus){
        if(basketItems != null && basketItems.size() != 0){
            orderService.checkout(basketItems,principal.getName());
            /*httpSession.removeAttribute("basketItems");*/
            //sessionStatus.setComplete();
            //httpSession.setAttribute("username",principal.getName());
            //httpSession.setAttribute("basketItems", new ArrayList<Item>());
        }
        return "redirect:/basket";
    }
}
