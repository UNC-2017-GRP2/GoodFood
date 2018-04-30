package com.netcracker.controller;

import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
import com.netcracker.model.User;
import com.netcracker.service.DrunkService;
import com.netcracker.service.Service;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class DrunkController {

    @Autowired
    private UserService userService;

    @Autowired
    private DrunkService drunkService;

    @RequestMapping(value = {"/drunk"}, method = RequestMethod.GET)
    public User drunkPage(ModelAndView model) throws IOException {
        //drunkService.callDriver(userService.getByUsername("user"));

        return userService.getByUsername("user");
    }
}
