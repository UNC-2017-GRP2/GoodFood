package com.victoria.controller;

import com.victoria.model.Item;
import com.victoria.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(value = {"username", "basketItems"})
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = { "/items"}, method = RequestMethod.GET)
    public ModelAndView itemsPage(@ModelAttribute String username) throws IOException {
        ModelAndView model = new ModelAndView("session_username");
        model.addObject(username);
        model.addObject("message","Заказ для пользователя ");
        model.addObject("add","Добавить в корзину");
        model.addObject("rub","\u20BD");
        model.setViewName("items");

        List<Item> allItems = itemService.getAllItems();
        if (allItems != null && allItems.size()!=0){
            model.addObject("items",allItems);
        }
        return model;
    }
    @RequestMapping(value = { "/items/{id}"}, method = RequestMethod.POST)
    public String addItemInBasket(@PathVariable BigInteger id, @ModelAttribute ArrayList<Item> basketItems, HttpSession httpSession) throws IOException {
        Item item = itemService.getItemById(id);
        if (item != null){
            if (httpSession.getAttribute("basketItems") == null){
                httpSession.setAttribute("basketItems", new ArrayList<Item>());
            }
            List<Item> curItems = (List<Item>) httpSession.getAttribute("basketItems");
            curItems.add(item);
            httpSession.setAttribute("basketItems", curItems);
        }
        return "redirect:/items";
    }


}
