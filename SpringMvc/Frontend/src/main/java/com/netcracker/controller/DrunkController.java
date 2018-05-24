package com.netcracker.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
import com.netcracker.model.MapParameter;
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

    @Autowired
    private DrunkService drunkService;

    @RequestMapping(value = "/drunk", method = RequestMethod.GET)
    public ModelAndView drunkPage(ModelAndView model) {
        model.setViewName("drunk");
        return model;
    }

    @RequestMapping(value = {"/drunk_rest"}, method = RequestMethod.GET)
    public String drunkRest(@RequestParam("input-address-latitude") String latitude,
                            @RequestParam("input-address-longitude") String longitude,
                            @RequestParam("input-address") String address,
                            @RequestParam("input-address-dest-latitude") String destLatitude,
                            @RequestParam("input-address-dest-longitude") String destLongitude,
                            @RequestParam("input-phone") String inputPhone,
                            Principal principal) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String originalInput = Constant.SOBER_DRIVER_LOGIN  + ":" + Constant.SOBER_DRIVER_PASS;
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
        JsonObject jobj = new Gson().fromJson(result, JsonObject.class);
        BigInteger orderId = jobj.get("id").getAsBigInteger();
        drunkService.addSobOrder(principal.getName(), orderId);
        return "redirect:/sober_list";
    }

    @RequestMapping(value = {"/sober_list"}, method = RequestMethod.GET)
    public ModelAndView soberList(ModelAndView model, Principal principal) {
        List<BigInteger> list = drunkService.getSobOrdersByUsername(principal.getName());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String originalInput = Constant.SOBER_DRIVER_LOGIN  + ":" + Constant.SOBER_DRIVER_PASS;
        String token = "Base " + Base64.getEncoder().encodeToString(originalInput.getBytes());
        headers.add("Authorization", token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        List<Entity> entityList = new ArrayList<>();
        for (BigInteger i : list) {
            ResponseEntity<String> userResponse = restTemplate.exchange(Constant.SOBER_DRIVER_URL + "/" + i, HttpMethod.GET, httpEntity, String.class);
            String result = userResponse.getBody();
            JsonObject jobj = new Gson().fromJson(result, JsonObject.class);

            String phoneNumber= jobj.get("clientPhoneNumber").getAsString();
            String address = jobj.get("address").getAsString();
            String geoData = jobj.get("geoData").getAsString();
            String destGeoData = jobj.get("destinationGeoData").getAsString();
            String startTime = jobj.get("orderStartTime").getAsString();
            String endTime = jobj.get("orderEndTime").getAsString();
            String statusOrder = jobj.get("statusOrder").getAsString();
            List<MapParameter> mapParameters = new ArrayList<>();
            mapParameters.add(new MapParameter(1, phoneNumber));
            mapParameters.add(new MapParameter(2, address));
            mapParameters.add(new MapParameter(3, geoData));
            mapParameters.add(new MapParameter(4, destGeoData));
            mapParameters.add(new MapParameter(5, startTime));
            mapParameters.add(new MapParameter(6, endTime));
            mapParameters.add(new MapParameter(7, statusOrder));
            Entity entity = new Entity(i, 1, "name", mapParameters);
            entityList.add(entity);
        }
        model.addObject(entityList);
        model.setViewName("sober_list");
        return model;
    }
}
