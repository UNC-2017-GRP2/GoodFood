package com.victoria.controller;

import com.victoria.config.AuthManager;
import com.victoria.model.User;
import com.victoria.service.UserService;
import com.victoria.validation.SignUpValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public static AuthenticationManager am = new AuthManager();

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registrationPost(@ModelAttribute ("userForm") @Validated User userForm, BindingResult result) {
        validator.validate(userForm, result);
        ModelAndView model = new ModelAndView();
        if (result.hasErrors()){
            model.addObject("flag","ToOpenRegistrationModal();");
            model.setViewName("login");
            return model;
        }else{
            ShaPasswordEncoder encoder = new ShaPasswordEncoder();
            userForm.setPasswordHash(encoder.encodePassword(userForm.getPasswordHash(),null));
            userForm.setRole("ROLE_USER");
            userService.saveUser(userForm);
            try{
                Authentication request = new UsernamePasswordAuthenticationToken(userForm.getLogin(), userForm.getPasswordHash());
                Authentication authResult = am.authenticate(request);
                SecurityContextHolder.getContext().setAuthentication(authResult);
                model.setViewName("redirect:/profile");
                return model;
            }catch (Exception e){
                System.out.println("Authentication failed: " + e.getMessage());
            }
            model.setViewName("login");
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

