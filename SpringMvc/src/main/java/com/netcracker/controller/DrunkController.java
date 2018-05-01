package com.netcracker.controller;

import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
import com.netcracker.model.User;
import com.netcracker.service.DrunkService;
import com.netcracker.service.Service;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class DrunkController {

    @Autowired
    private UserService userService;

    @Autowired
    private DrunkService drunkService;

    @RequestMapping(value = {"/drunk_receive"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void drunkNotifyPage(@RequestBody User user) {
        //drunkService.callDriver(userService.getByUsername("user"));

//        return userService.getByUsername("user");
        System.out.println(user.getFio());
    }

    @RequestMapping(value = {"/drunk"}, method = RequestMethod.GET)
    public void drunkPage() throws IOException {


//        return userService.getByUsername("user");
        drunkService.callDriver(userService.getByUsername("user"));

    }
}
