package com.netcracker.controller;


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

@Controller
@SessionAttributes(value = {"username","basketItems"})
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;



    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public ModelAndView homeValue(ModelAndView model,@RequestParam(value = "value", required = false) String value,Principal principal, HttpSession httpSession) throws IOException {
        if (principal != null){
            User user  = userService.getByUsername(principal.getName());
            if (httpSession.getAttribute("username") == null){
                httpSession.setAttribute("username",principal.getName());
            }
            if (httpSession.getAttribute("basketItems") == null){
                httpSession.setAttribute("basketItems", new ArrayList<Item>());
            }
            if (httpSession.getAttribute("userAddresses") == null){
                httpSession.setAttribute("userAddresses", user.getAddresses());
            }
            if (httpSession.getAttribute("userPhone") == null){
                httpSession.setAttribute("userPhone", user.getPhoneNumber());
            }
        }
        List<Item> currentItems = itemService.getItemsByCategory(value);
        if(value == null){
            model.addObject("value", "Pizza");
        }else{
            model.addObject("value", value);
        }
        if (currentItems == null){
            currentItems = itemService.getItemsByCategory("Pizza");

        }
        model.addObject("items", currentItems);
        model.addObject("rub","\u20BD");
        model.addObject("add","Add to Basket");
        //model.addObject("notification", null);
        model.setViewName("home");
        return model;
    }


    @RequestMapping(value = { "/addBasket"}, method = RequestMethod.POST)
    public ModelAndView addItemInBasket(@RequestParam("id") BigInteger id, @RequestParam("count") int count, HttpSession httpSession) throws IOException {
        Item item = itemService.getItemById(id);
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
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/home?value="+item.getProductCategory());
        //model.addObject("notification","Successfully added to the basket");
        return model;
    }

}
