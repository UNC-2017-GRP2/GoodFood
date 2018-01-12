package com.victoria.controller;

import com.victoria.model.User;
import com.victoria.service.UserService;
import com.victoria.validation.SignUpValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private SignUpValidator validator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationGet(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registrationPost(@ModelAttribute ("userForm") @Validated User userForm, BindingResult result) {
        validator.validate(userForm, result);
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        if (result.hasErrors()){
            model.addObject("flag","ToOpenRegistrationModal();");
            return model;
        }else{
            ShaPasswordEncoder encoder = new ShaPasswordEncoder();
            userForm.setPasswordHash(encoder.encodePassword(userForm.getPasswordHash(),null));
            userForm.setRole("ROLE_USER");
            userService.saveUser(userForm);
            model.addObject("flag","ToCleanRegistrationForm();");
            return model;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error) {
        ModelAndView model = new ModelAndView();
        try{
            if (error != null) {
                model.addObject("error", "Invalid Credentials provided.");
            }
            model.setViewName("login");
            model.addObject("userForm", new User());
            return model;
        }catch(Exception e){
            System.out.println("method loginPage:" + e.getMessage());
        }
        return model;
    }
}
