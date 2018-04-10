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

    class Response{
        private BigInteger objectId;
        private String name;

        public BigInteger getObjectId() {
            return objectId;
        }

        public String getName() {
            return name;
        }

        public void setObjectId(BigInteger objectId) {
            this.objectId = objectId;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    @RequestMapping(value = {"/sendEntity"}, method = RequestMethod.GET)
    public @ResponseBody Response sendEntity(@RequestParam String jsonEntity) throws IOException {
        Gson gson = new Gson();
        Entity entity = gson.fromJson(jsonEntity, Entity.class);
        service.saveEntity(entity);

        Response response = new Response();
        response.setName("success");
        response.setObjectId(new BigInteger("12345615"));
        return response;
    }
}
