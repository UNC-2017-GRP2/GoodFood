package com.netcracker.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netcracker.service.DrunkService;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

@RestController
public class DrunkController {

    @Autowired
    private UserService userService;

    @Autowired
    private DrunkService drunkService;

    @RequestMapping(value = {"/drunk"}, method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
    public @ResponseBody String drunkPage() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String originalInput = "SoberDriverVendor" + ":" + "4u76wgjko9";
        String token = "Base " + Base64.getEncoder().encodeToString(originalInput.getBytes());
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        JsonObject user_auth = new JsonObject();
        user_auth.addProperty("clientFirstName", "Client");
        user_auth.addProperty("clientLastName", "LastName");
        user_auth.addProperty("clientPhoneNumber", "+70000000000");
        user_auth.addProperty("address", "Воронеж, Коминтерновский район, Северный жилой район");
        user_auth.addProperty("geoData", "51.699273121536756,39.13550155951171");
        user_auth.addProperty("destinationGeoData", "51.69244577035972,39.17979019476561");

        Gson gson = new Gson();
        String payload = gson.toJson(user_auth);
        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        System.out.println(entity.toString());
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseEntity<String> userResponse = restTemplate.exchange("http://185.246.65.240:8080/app/vendor/orders", HttpMethod.POST, entity, String.class);
        String result = userResponse.getBody();
        return result;
    }
}
