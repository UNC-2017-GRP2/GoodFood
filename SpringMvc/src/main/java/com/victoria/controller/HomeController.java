package com.victoria.controller;


import com.victoria.model.Item;
import com.victoria.model.User;
import com.victoria.service.ItemService;
import com.victoria.service.UserService;
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

@Controller
@SessionAttributes(value = {"username","basketItems"})
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    /*@RequestMapping(value = { "/home", "/"}, method = RequestMethod.GET)
    public ModelAndView homePage(ModelAndView model) throws IOException {
        List<Item> currentItems = itemService.getAllItems();
        model.addObject("items",currentItems);
        model.setViewName("home");
        return model;
    }*/


    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public ModelAndView homeValue(ModelAndView model,@RequestParam(value = "value", required = false) String value,Principal principal, HttpSession httpSession) throws IOException {
        if (httpSession.getAttribute("username") == null){
            httpSession.setAttribute("username",principal.getName());
        }
        if (httpSession.getAttribute("basketItems") == null){
            httpSession.setAttribute("basketItems", new ArrayList<Item>());
        }
        List<Item> currentItems = itemService.getItemsByCategory(value);
        if (currentItems == null){
            currentItems = itemService.getItemsByCategory("Pizza");
        }
        model.addObject("items", currentItems);
        model.addObject("rub","\u20BD");
        model.addObject("add","Add to Basket");
        model.addObject("value", value);
        model.setViewName("home");
        return model;
    }

}

