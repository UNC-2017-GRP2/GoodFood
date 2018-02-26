package com.netcracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class CurrentOrdersController {

    @RequestMapping(value = { "/current-orders"}, method = RequestMethod.GET)
    public ModelAndView currentOrders(ModelAndView model) throws IOException {
        model.setViewName("current-orders");
        return model;
    }
}
