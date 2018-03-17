package com.netcracker.controller;

import com.netcracker.config.AuthManager;
import com.netcracker.model.Address;
import com.netcracker.model.Item;
import com.netcracker.model.User;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(value = {"username"})
public class ProfileController {

    @Autowired
    private UserService userService;

    public static AuthenticationManager am = new AuthManager();

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public ModelAndView profilePage(ModelAndView model, Principal principal, HttpSession httpSession) {
        try {
            User user = userService.getByUsername(principal.getName());
            if (user != null) {
                model.addObject("user", user);

                model.addObject("userForUpdate", user);
                if (httpSession.getAttribute("username") == null) {
                    httpSession.setAttribute("username", principal.getName());
                }
                if (httpSession.getAttribute("basketItems") == null) {
                    httpSession.setAttribute("basketItems", new ArrayList<Item>());
                }
                if (httpSession.getAttribute("userAddresses") == null) {
                    httpSession.setAttribute("userAddresses", user.getAddresses());
                }
                if (httpSession.getAttribute("newAddresses") == null) {
                    httpSession.setAttribute("newAddresses", new ArrayList<>(user.getAddresses()));
                }
            }
            model.addObject("nullParameter", "None");
            model.setViewName("profile");
            return model;
        } catch (Exception e) {
            System.out.println("method profilePage:" + e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.GET)
    public ModelAndView editPage(ModelAndView model, Principal principal){
        User user = userService.getByUsername(principal.getName());
        if (user != null) {
            model.addObject("user", user);
        }
        model.setViewName("redirect:/profile");
        return model;
    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute("userForUpdate") User updatedUser, Principal principal, HttpSession httpSession, SessionStatus sessionStatus) {
        ModelAndView model = new ModelAndView();
        try{
            User user = userService.getByUsername(principal.getName());
            updatedUser.setPasswordHash(user.getPasswordHash());
            userService.updateUser(user, updatedUser);
            if (!user.getLogin().equals(updatedUser.getLogin())) {
                Authentication request = new UsernamePasswordAuthenticationToken(updatedUser.getLogin(), updatedUser.getPasswordHash());
                Authentication authResult = am.authenticate(request);
                SecurityContextHolder.getContext().setAuthentication(authResult);
            }
            sessionStatus.setComplete();
            httpSession.setAttribute("username", updatedUser.getLogin());
            httpSession.setAttribute("userPhone", updatedUser.getPhoneNumber());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            model.setViewName("redirect:/profile");
            return model;
        }
    }

    @RequestMapping(value = {"/editPassword"}, method = RequestMethod.POST)
    public ModelAndView editPassword(@ModelAttribute("userForUpdate") User updatedUser, Principal principal) {
        ModelAndView model = new ModelAndView();
        try{
            User user = userService.getByUsername(principal.getName());
            ShaPasswordEncoder encoder = new ShaPasswordEncoder();
            String hashNewPassword = encoder.encodePassword(updatedUser.getPasswordHash(), null);
            userService.updatePassword(user.getUserId(), hashNewPassword);
            Authentication request = new UsernamePasswordAuthenticationToken(principal.getName(), hashNewPassword);
            Authentication authResult = am.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(authResult);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            model.setViewName("redirect:/profile");
            return model;
        }
    }

    @RequestMapping(value = "/addAddress", method = RequestMethod.GET)
    public @ResponseBody
    String addAddress(@RequestParam double latitude, @RequestParam double longitude, HttpSession httpSession) {
        List<Address> newAddresses = (List<Address>) httpSession.getAttribute("newAddresses");
        for (Address address : newAddresses) {
            if (address.getLatitude() == latitude && address.getLongitude() == longitude) {
                return "isExist";
            }
        }
        newAddresses.add(new Address(latitude, longitude));
        httpSession.setAttribute("newAddresses", newAddresses);
        return "success";
    }

    @RequestMapping(value = "/removeAddress", method = RequestMethod.GET)
    public @ResponseBody
    void removeAddress(@RequestParam double latitude, @RequestParam double longitude, HttpSession httpSession) {
        List<Address> newAddresses = (List<Address>) httpSession.getAttribute("newAddresses");
        for (Address address : newAddresses) {
            if (address.getLatitude() == latitude && address.getLongitude() == longitude) {
                newAddresses.remove(address);
                break;
            }
        }
        httpSession.setAttribute("newAddresses", newAddresses);
    }

    @RequestMapping(value = "/resetNewAddress", method = RequestMethod.GET)
    public @ResponseBody
    void resetNewAddress(HttpSession httpSession) {
        List<Address> newAddresses = (ArrayList<Address>) httpSession.getAttribute("userAddresses");
        httpSession.setAttribute("newAddresses", new ArrayList<>(newAddresses));
    }

    @RequestMapping(value = {"/editAddresses"}, method = RequestMethod.GET)
    public ModelAndView editAddresses(Principal principal, HttpSession httpSession) {
        ModelAndView model = new ModelAndView();
        BigInteger userId = userService.getByUsername(principal.getName()).getUserId();
        List<Address> newAddresses = (ArrayList<Address>) httpSession.getAttribute("newAddresses");
        userService.updateAddresses(userId, newAddresses);
        httpSession.setAttribute("userAddresses", new ArrayList<>(newAddresses));
        model.setViewName("redirect:/profile");
        return model;
    }

    @RequestMapping(value = "/checkUsernameForUpdate", method = RequestMethod.GET)
    public @ResponseBody
    String checkUsername(@RequestParam String username, Principal principal) {
        if (userService.isLoginExist(username)) {
            User user = userService.getByUsername(principal.getName());
            if (!userService.isYourLoginForUpdateUser(username, user.getPasswordHash())) {
                return "true";
            }
        }
        return "false";
    }

    @RequestMapping(value = "/checkEmailForUpdate", method = RequestMethod.GET)
    public @ResponseBody
    String checkEmail(@RequestParam String email, Principal principal) {
        if (userService.isEmailExist(email)) {
            User user = userService.getByUsername(principal.getName());
            if (!userService.isYourEmailForUpdateUser(email, user.getPasswordHash())) {
                return "true";
            }
        }
        return "false";
    }

    @RequestMapping(value = "/checkPasswordForUpdate", method = RequestMethod.GET)
    public @ResponseBody
    String checkPassword(@RequestParam String oldPassword, Principal principal) {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        User user = userService.getByUsername(principal.getName());
        if (!userService.isEqualsPassword(encoder.encodePassword(oldPassword, null), user.getUserId())) {
            return "false";
        }
        return "true";
    }
}
