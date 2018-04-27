package com.netcracker.controller;

import com.netcracker.config.AuthManager;
import com.netcracker.model.User;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    public static AuthenticationManager am = new AuthManager();

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registrationPost(@ModelAttribute("userForm") @Validated User userForm) {
        ModelAndView model = new ModelAndView();
        try {
            ShaPasswordEncoder encoder = new ShaPasswordEncoder();
            userForm.setPasswordHash(encoder.encodePassword(userForm.getPasswordHash(), null));
            userForm.setRole("ROLE_USER");
            userService.saveUser(userForm);

            Authentication request = new UsernamePasswordAuthenticationToken(userForm.getLogin(), userForm.getPasswordHash());
            Authentication authResult = am.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            model.setViewName("redirect:/profile");
            return model;
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
        }
        finally {
            model.setViewName("redirect:/login");
            return model;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error) {
        ModelAndView model = new ModelAndView();
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            model.setViewName("redirect:/home");
            return model;
        }
        try {
            if (error != null) {
                model.addObject("error", "Invalid Credentials provided.");
            }
            model.setViewName("login");
            model.addObject("userForm", new User());
            return model;
        } catch (Exception e) {
            System.out.println("method loginPage:" + e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = "/checkUsername", method = RequestMethod.GET)
    public @ResponseBody
    String checkUsername(@RequestParam String username) {
        if (userService.isLoginExist(username)) {
            return "true";
        } else {
            return "false";
        }
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
    public @ResponseBody
    String checkEmail(@RequestParam String email) {
        if (userService.isEmailExist(email)) {
            return "true";
        } else {
            return "false";
        }
    }
}

