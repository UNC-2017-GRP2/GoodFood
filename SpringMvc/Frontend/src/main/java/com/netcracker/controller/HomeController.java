package com.netcracker.controller;


import com.netcracker.config.Constant;
import com.netcracker.model.Item;
import com.netcracker.model.User;
import com.netcracker.service.ItemService;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes(value = {"username","basketItems","cartSize1"})
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public ModelAndView homeValue(ModelAndView model, @RequestParam(value = "value", required = false) String value, Locale locale, Principal principal, HttpSession httpSession) throws IOException {
        if (principal != null){
            User user  = userService.getByUsername(principal.getName());
            if (httpSession.getAttribute("username") == null){
                httpSession.setAttribute("username",principal.getName());
            }
            if (!user.getRole().equals(Constant.ROLE_COURIER)){
                if (httpSession.getAttribute("basketItems") == null){
                    httpSession.setAttribute("basketItems", new ArrayList<Item>());
                }else{
                    int val = 0;
                    for (Item item:(List<Item>) httpSession.getAttribute("basketItems")){
                        val+=item.getProductQuantity();
                    }
                    model.addObject("cartSize", val);
                }
            }
            if (httpSession.getAttribute("userAddresses") == null){
                httpSession.setAttribute("userAddresses", user.getAddresses());
            }
            if (httpSession.getAttribute("userPhone") == null){
                httpSession.setAttribute("userPhone", user.getPhoneNumber());
            }
            if (user.getRole().equals(Constant.ROLE_COURIER)) {
                model.setViewName("redirect:/my-orders/1");
                return model;
            }
        }

        List<Item> currentItems = itemService.getItemsByCategory(value, locale);
        if(value == null){
            model.addObject("value", Constant.CATEGORY_PIZZA);
        }else{
            model.addObject("value", value);
            if(value.equals(String.valueOf(Constant.CATEGORY_BEVERAGES))) {
                model.addObject("bottle", 1);
            }
            if(value.equals(String.valueOf(Constant.CATEGORY_ALCOHOL))) {
                model.addObject("car", 1);
            }
        }
        if (currentItems == null || currentItems.size() == 0){
            model.addObject("value", Constant.CATEGORY_PIZZA);
            currentItems = itemService.getItemsByCategory(String.valueOf(Constant.CATEGORY_PIZZA), locale);
        }
        model.addObject("items", currentItems);


        model.setViewName("home");
        return model;
    }


    /*@RequestMapping(value = { "/addBasket"}, method = RequestMethod.GET)
    public String addBasket(ModelAndView model, @RequestParam BigInteger id, @RequestParam int count, Locale locale, HttpSession httpSession) throws IOException {
        Item item = itemService.getItemById(id, locale);
        int val = 0;
        if (item != null){
            if (httpSession.getAttribute("basketItems") == null){
                httpSession.setAttribute("basketItems", new ArrayList<Item>());
            }
            *//*if (httpSession.getAttribute("cartSize") == null){
                httpSession.setAttribute("cartSize", 0);
            }*//*
            List<Item> curItems = (List<Item>) httpSession.getAttribute("basketItems");
            boolean itemIsInBasket = false;
            for(Item itemInBasket : curItems){
                if(itemInBasket.getProductId().equals(item.getProductId())){
                    itemInBasket.setProductQuantity(itemInBasket.getProductQuantity() + count);
                    itemIsInBasket = true;
                    break;
                }
            }
            if(!itemIsInBasket){
                item.setProductQuantity(count);
                curItems.add(item);
            }
            httpSession.setAttribute("basketItems", curItems);
            //httpSession.removeAttribute("cartSize");
            //int newSize = (int) httpSession.getAttribute("cartSize");
            //Integer newSize = (Integer)httpSession.getAttribute("cartSize");
            *//*
            newSize = val;*//*
            //newSize = new Integer(newSize.intValue() + val);
            //httpSession.removeAttribute("cartSize");

            //httpSession.setAttribute("cartSize", newSize);
            *//*httpSession.setAttribute("cartSize", ((ArrayList<Item>)httpSession.getAttribute("basketItems")).size());*//*

            for (Item curItem:curItems){
                val+=curItem.getProductQuantity();
            }
            model.addObject("cartSize", val);
        }
        model.setViewName("redirect:/home");
        String v = String.valueOf(val);
        return "hello";
    }*/

    @RequestMapping(value = "/addBasket", method = RequestMethod.GET)
    public @ResponseBody String addBasket(@RequestParam BigInteger id, @RequestParam int count, Locale locale, HttpSession httpSession){
        Item item = itemService.getItemById(id, locale);
        int val = 0;
        if (item != null){
            if (httpSession.getAttribute("basketItems") == null){
                httpSession.setAttribute("basketItems", new ArrayList<Item>());
            }
            List<Item> curItems = (List<Item>) httpSession.getAttribute("basketItems");
            boolean itemIsInBasket = false;
            for(Item itemInBasket : curItems){
                if(itemInBasket.getProductId().equals(item.getProductId())){
                    itemInBasket.setProductQuantity(itemInBasket.getProductQuantity() + count);
                    itemIsInBasket = true;
                    break;
                }
            }
            if(!itemIsInBasket){
                item.setProductQuantity(count);
                curItems.add(item);
            }
            httpSession.setAttribute("basketItems", curItems);
            for (Item curItem:curItems){
                val+=curItem.getProductQuantity();
            }
        }
        return String.valueOf(val);
    }
}

