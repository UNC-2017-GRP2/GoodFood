package com.victoria.controller;

import com.victoria.model.Item;
import com.victoria.model.User;
import com.victoria.service.UserService;
import com.victoria.validation.EditProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @Autowired
    private EditProfileValidator validator;

    @RequestMapping(value = { "/profile"}, method = RequestMethod.GET)
    public ModelAndView profilePage(ModelAndView model, Principal principal, HttpSession httpSession) throws IOException {
        try{
            User user  = userService.getByUsername(principal.getName());
            if (user != null){
                model.addObject("user", user);
                httpSession.setAttribute("username",principal.getName());
                httpSession.setAttribute("basketItems", new ArrayList<Item>());
            }
            model.addObject("nullParameter", "None");
            model.setViewName("profile");
            return model;
        }catch (Exception e){
            System.out.println("method profilePage:" + e.getMessage());
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
    public ModelAndView edit(@ModelAttribute("user") @Validated User updatedUser,Principal principal, BindingResult result) throws IOException {
        User user  = userService.getByUsername(principal.getName());
        updatedUser.setPasswordHash(user.getPasswordHash());
        validator.validate(updatedUser, result);
        ModelAndView model = new ModelAndView();
        if (result.hasErrors()){
            model.addObject("flag","ToOpenEditModal();");
            model.setViewName("profile");
            return model;
        }else{
            userService.updateUser(user,updatedUser);
            model.addObject("nullParameter", "None");
            model.addObject("flag","ToCleanEditProfileForm();");
            model.setViewName("redirect:/profile");
            return model;
        }
    }

    @RequestMapping(value = { "/editPassword"}, method = RequestMethod.POST)
    public String editPassword(){
        return "redirect:/home";
    }
}
