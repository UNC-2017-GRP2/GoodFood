package com.netcracker.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netcracker.config.Constant;
import com.netcracker.model.Item;
import com.netcracker.model.User;
import com.netcracker.service.DrunkService;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@SessionAttributes(value = {"username"})
public class DrunkController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/drunk", method = RequestMethod.GET)
    public ModelAndView drunkPage(ModelAndView model) {
        model.setViewName("drunk");
        return model;
    }

    @RequestMapping(value = {"/drunk_rest"}, method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
    public @ResponseBody String drunkRest(@RequestParam("input-address-latitude") String latitude,
                                          @RequestParam("input-address-longitude") String longitude,
                                          @RequestParam("input-address") String address,
                                          @RequestParam("input-address-dest-latitude") String destLatitude,
                                          @RequestParam("input-address-dest-longitude") String destLongitude,
                                          @RequestParam("input-phone") String inputPhone,
                                          Principal principal) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String originalInput = "SoberDriverVendor" + ":" + "4u76wgjko9";
        String token = "Base " + Base64.getEncoder().encodeToString(originalInput.getBytes());
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        User user  = userService.getByUsername(principal.getName());
        JsonObject user_auth = new JsonObject();
        user_auth.addProperty("clientFirstName", user.getLogin());
        user_auth.addProperty("clientLastName", user.getFio());
        user_auth.addProperty("clientPhoneNumber", inputPhone);
        user_auth.addProperty("address", address);
        user_auth.addProperty("geoData", latitude + "," + longitude);
        user_auth.addProperty("destinationGeoData", destLatitude + "," + destLongitude);

        Gson gson = new Gson();
        String payload = gson.toJson(user_auth);
        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        System.out.println(entity.toString());
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseEntity<String> userResponse = restTemplate.exchange(Constant.SOBER_DRIVER_URL, HttpMethod.POST, entity, String.class);
        String result = userResponse.getBody();
        return result;
    }
}
