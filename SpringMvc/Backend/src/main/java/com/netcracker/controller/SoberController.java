package com.netcracker.controller;

import com.netcracker.service.DrunkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/sober")
public class SoberController {

    @Autowired
    DrunkService drunkService;

    @RequestMapping(value = "/add/{username}/{orderid}", method = RequestMethod.GET)
    public void addSoberOrder(@PathVariable String username, @PathVariable BigInteger orderid) {
        drunkService.addSobOrder(username, orderid);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public List<BigInteger> getSoberOrders(@PathVariable String username) {
        return drunkService.getSobOrdersByUsername(username);
    }
}