package com.victoria.controller;


import com.victoria.model.Item;
import com.victoria.model.User;
import com.victoria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;

@Controller
@SessionAttributes(value = {"username","basketItems"})
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/home", "/"}, method = RequestMethod.GET)
    public ModelAndView homePage(ModelAndView model, Principal principal, HttpSession httpSession) throws IOException {
        try{
            User user  = userService.getByUsername(principal.getName());
            if (user != null){
                model.addObject("user", user);
                httpSession.setAttribute("username",principal.getName());
                httpSession.setAttribute("basketItems", new ArrayList<Item>());
            }
            model.addObject("nullParameter", "Отсутствует");
            model.setViewName("home");
            return model;
        }catch (Exception e){
            System.out.println("method homePage:" + e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = { "/edit"}, method = RequestMethod.GET)
    public ModelAndView editPage(ModelAndView model, Principal principal) throws IOException {
        User user  = userService.getByUsername(principal.getName());
        if (user != null){
            model.addObject("user", user);
        }
        model.setViewName("edit");
        return model;
    }

    @RequestMapping(value = { "/edit"}, method = RequestMethod.POST)
    public String edit(@ModelAttribute("user") User updatedUser, ModelAndView model, Principal principal) throws IOException {
        User user  = userService.getByUsername(principal.getName());
        userService.updateUser(user,updatedUser);
        model.addObject("nullParameter", "Отсутствует");
        model.setViewName("home");
        return "redirect:/home";
    }
}

