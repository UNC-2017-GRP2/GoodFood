package com.netcracker.service.impl;

import com.netcracker.config.Constant;
import com.netcracker.model.Item;
import com.netcracker.model.User;
import com.netcracker.service.DrunkService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.Locale;

@Service
public class DrunkServiceImpl implements DrunkService {

    @Override
    public void callDriver(User user) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User> request = new HttpEntity<>(user);

        restTemplate.exchange(Constant.BASE_URL_REST + "/",
                HttpMethod.PUT, request, new ParameterizedTypeReference<User>() {
                });
    }
}
