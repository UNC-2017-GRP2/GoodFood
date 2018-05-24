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
import java.util.List;
import java.util.Locale;

@Service
public class DrunkServiceImpl implements DrunkService {

    @Override
    public void addSobOrder(String username, BigInteger orderId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>("");
        restTemplate.exchange(Constant.BASE_URL_REST + "/sober/add/" + username + "/" + orderId,
                HttpMethod.GET, request, String.class);
    }

    @Override
    public List<BigInteger> getSobOrdersByUsername(String username) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<BigInteger>> response =
                restTemplate.exchange(Constant.BASE_URL_REST + "/sober/" + username,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<BigInteger>>() {
                        });
        List<BigInteger> result = response.getBody();
        return result;
    }
}
