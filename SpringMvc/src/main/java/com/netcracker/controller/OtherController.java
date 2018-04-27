package com.netcracker.controller;

import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
import com.netcracker.model.Item;
import com.netcracker.model.User;
import com.netcracker.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class OtherController {

    @Autowired
    private Repository repository;

    @RequestMapping(value = {"/other"}, method = RequestMethod.GET)
    public ModelAndView otherPage(ModelAndView model) throws IOException {
        List<Entity> users = repository.getEntitiesByObjectTypeId(Constant.USER_OBJ_TYPE_ID);
        model.setViewName("other");
        model.addObject("users", users);
        return model;
    }
}
