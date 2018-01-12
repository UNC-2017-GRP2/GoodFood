package com.victoria.controller;

import com.victoria.model.Item;
import com.victoria.model.User;
import com.victoria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;

@Controller
@SessionAttributes(value = {"username","basketItems"})
public class ProfileController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/profile"}, method = RequestMethod.GET)
    public ModelAndView profilePage(ModelAndView model, Principal principal, HttpSession httpSession) throws IOException {
        try{
            User user  = userService.getByUsername(principal.getName());
            if (user != null){
                model.addObject("user", user);
                httpSession.setAttribute("username",principal.getName());
                httpSession.setAttribute("basketItems", new ArrayList<Item>());
            }
            model.addObject("nullParameter", "Отсутствует");
            model.setViewName("profile");
            return model;
        }catch (Exception e){
            System.out.println("method profilePage:" + e.getMessage());
        }
        return model;
    }
}
