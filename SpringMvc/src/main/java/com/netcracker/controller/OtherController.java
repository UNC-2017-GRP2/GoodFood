package com.netcracker.controller;

import com.google.gson.Gson;
import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
import com.netcracker.repository.Repository;
import com.netcracker.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Controller
public class OtherController {

    @Autowired
    private Repository repository;

    @Autowired
    private Service service;

    @RequestMapping(value = {"/other"}, method = RequestMethod.GET)
    public ModelAndView otherPage(ModelAndView model) throws IOException {
        List<Entity> users = repository.getEntitiesByObjectTypeId(Constant.USER_OBJ_TYPE_ID);
        model.setViewName("other");
        model.addObject("users", users);
        return model;
    }


    @RequestMapping(value = {"/sendEntity"}, method = RequestMethod.GET)
    public @ResponseBody String sendEntity(@RequestParam String jsonEntity) throws IOException {
        Gson gson = new Gson();
        Entity entity = gson.fromJson(jsonEntity, Entity.class);
        service.saveEntity(entity);

        return "redirect:/home";
    }
}
