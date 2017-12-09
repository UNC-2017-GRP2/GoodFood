package com.netcracker.edu.controller;

import java.io.IOException;
import java.util.List;

import com.netcracker.edu.model.UserInfo;
import com.netcracker.edu.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private MyService myService;

    @RequestMapping(value = { "/"}, method = RequestMethod.GET)
    public ModelAndView welcomePage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("welcomePage");
        return model;
    }

    @RequestMapping(value = { "/homePage"}, method = RequestMethod.GET)
    public ModelAndView homePage(ModelAndView model) throws IOException {
        List<UserInfo> objectList = myService.list();
        objectList.add(myService.getByUsername("admin1"));
        model.addObject("objectList", objectList);
        model.setViewName("homePage");
        return model;
    }

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
                                  @RequestParam(value = "logout",	required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid Credentials provided.");
        }

        if (logout != null) {
            model.addObject("message", "Logged out from JournalDEV successfully.");
        }

        model.setViewName("loginPage");
        return model;
    }

}
