package com.netcracker.controller;

import com.netcracker.model.Item;
import com.netcracker.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(value = {"basketItems"})
public class BasketController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public ModelAndView toBasket(HttpSession httpSession){
        ModelAndView model = new ModelAndView();
        model.addObject("rub","\u20BD");
        List<Item> basketItems = (ArrayList<Item>) httpSession.getAttribute("basketItems");
        if(basketItems != null && basketItems.size() != 0){
            BigInteger summa = orderService.totalOrder((ArrayList<Item>) basketItems);
            model.addObject("totalOrder", summa);
        }
        model.setViewName("basket");
        return model;
    }


    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(Principal principal, HttpSession httpSession, SessionStatus sessionStatus){
        ArrayList<Item> basketItems = (ArrayList<Item>)httpSession.getAttribute("basketItems");
        if(basketItems != null && basketItems.size() != 0){
            orderService.checkout(basketItems,principal.getName());
             sessionStatus.setComplete();
            return "redirect:/my-orders";
        }else{
            return "redirect:/basket";
        }

    }

    @RequestMapping(value = "/updateBasket", method = RequestMethod.GET)
    public @ResponseBody void updateBasket(@RequestParam BigInteger itemId, @RequestParam int newQuantity, HttpSession httpSession){

        List<Item> curItems = (List<Item>) httpSession.getAttribute("basketItems");
        for(Item itemInBasket : curItems){
            if(itemInBasket.getProductId().equals(itemId)){
                itemInBasket.setProductQuantity(newQuantity);
                break;
            }
        }
        httpSession.setAttribute("basketItems", curItems);
    }

    @RequestMapping(value = "/removeItem", method = RequestMethod.GET)
    public @ResponseBody String removeItem(@RequestParam BigInteger itemId, HttpSession httpSession){
        List<Item> curItems = (List<Item>) httpSession.getAttribute("basketItems");
        for(Item itemInBasket : curItems){
            if(itemInBasket.getProductId().equals(itemId)){
                curItems.remove(itemInBasket);
                break;
            }
        }
        httpSession.setAttribute("basketItems", curItems);
        BigInteger summa = orderService.totalOrder((ArrayList<Item>) curItems);
        return summa.toString();
    }

    @RequestMapping(value = "/isBasketEmpty", method = RequestMethod.GET)
    public @ResponseBody String isBasketEmpty(HttpSession httpSession){
        List<Item> curItems = (List<Item>) httpSession.getAttribute("basketItems");
        if(curItems == null || curItems.size() == 0){
            return "true";
        }else{
            return "false";
        }
    }



}
