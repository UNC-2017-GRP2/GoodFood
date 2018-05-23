package com.netcracker.service.impl;

import com.netcracker.config.Constant;
import com.netcracker.model.Address;
import com.netcracker.model.User;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final String USER_BASE_URL = Constant.BASE_URL_REST + "/users";

    @Override
    public User getUserById(BigInteger userId) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<User> userResponse =
                restTemplate.exchange(USER_BASE_URL+"/id/" + userId + "/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<User>() {
                        });
        User result = userResponse.getBody();

        return result;
    }

    @Override
    public User getByUsername(String username){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<User> userResponse =
                restTemplate.exchange(USER_BASE_URL+"/username/" + username + "/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<User>() {
                        });
        User result = userResponse.getBody();

        return result;
    }

    @Override
    public void saveUser(User user) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User> request = new HttpEntity<>(user);

        restTemplate.exchange(USER_BASE_URL+"/save/",
                HttpMethod.POST, request, new ParameterizedTypeReference<User>() {
                });
        //TODO:разобраться, что за дерьмо тут происходит
        /*
         restTemplate.postForLocation("http://127.0.0.1:8080/rest/users/save/", request);
         */
    }

    @Override
    public void updateUser(User oldUser, User user) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User> request = new HttpEntity<>(user);

        restTemplate.exchange(USER_BASE_URL+"/username/" + oldUser.getLogin() + "/",
                HttpMethod.PUT, request, new ParameterizedTypeReference<User>() {
                });
    }

    @Override
    public boolean isLoginExist(String login) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Boolean> response =
                restTemplate.exchange(USER_BASE_URL+"/validate/login/" + login + "/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<Boolean>() {
                        });
        boolean result = response.getBody();

        return result;
    }

    @Override
    public List<User> getAllUsers() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<User>> userResponse =
                restTemplate.exchange(USER_BASE_URL+"/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                        });
        List<User> result = userResponse.getBody();

        return result;
    }

    @Override
    public boolean isEmailExist(String email) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Boolean> response =
                restTemplate.exchange(USER_BASE_URL+"/validate/email/" + email + "/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<Boolean>() {
                        });
        boolean result = response.getBody();

        return result;
    }

    @Override
    public boolean isYourLoginForUpdateUser(String login, String password) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(password);

        ResponseEntity<Boolean> response =
                restTemplate.exchange(USER_BASE_URL+"/validate/login/" + login + "/",
                        HttpMethod.POST, request, new ParameterizedTypeReference<Boolean>() {
                        });
        boolean result = response.getBody();

        return result;
    }

    @Override
    public boolean isYourEmailForUpdateUser(String email, String password) {
        RestTemplate restTemplate = new RestTemplate();

        //HttpEntity<String> request = new HttpEntity<>(password);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

        body.add("password", password);

        HttpHeaders headers=new HttpHeaders();
        headers.set("Content-Type", "application/json");

// Note the body object as first parameter!
        //HttpEntity<?> request = new HttpEntity<Object>(body,headers);
        RequestEntity<String> request = RequestEntity
                .post(URI.create(USER_BASE_URL+"/validate/email/" + email))
                .accept(MediaType.APPLICATION_JSON)
                .body(password);

        /*String url = USER_BASE_URL+"/validate/email/" + email;
        //TODO: разобраться, из за чего здесь выдается 403 ошибка
        ResponseEntity<Boolean> response =
                restTemplate.exchange(url,
                        HttpMethod.POST, request, Boolean.class);
        Boolean result = response.getBody();
        */
        ResponseEntity<Boolean> response = restTemplate.exchange(request,Boolean.class);
        Boolean result = response.getBody();
        return result;
    }

    @Override
    public boolean isEqualsPassword(String password, BigInteger userId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(password);

        ResponseEntity<Boolean> response =
                restTemplate.exchange(USER_BASE_URL+"/validate/password/id/" + userId + "/",
                        HttpMethod.POST, request, new ParameterizedTypeReference<Boolean>() {
                        });
        boolean result = response.getBody();

        return result;
    }

    @Override
    public void updatePassword(BigInteger userId, String password) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(password);

        restTemplate.exchange(USER_BASE_URL+"/id/" + userId + "/update/password/",
                HttpMethod.PUT, request, new ParameterizedTypeReference<User>() {
                });
    }

    @Override
    public void updateAddresses(BigInteger userId, List<Address> addresses) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<List<Address>> request = new HttpEntity<>(addresses);

        restTemplate.exchange(USER_BASE_URL+"/id/" + userId + "/update/addresses/",
                HttpMethod.PUT, request, new ParameterizedTypeReference<User>() {
                });
    }

    @Override
    public void changeRole(BigInteger userId, String role) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(role);

        restTemplate.exchange(USER_BASE_URL+"/id/" + userId + "/update/role/",
                HttpMethod.PUT, request, new ParameterizedTypeReference<User>() {
                });
    }

    @Override
    public void removeUserById(BigInteger userId) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> userResponse =
                restTemplate.exchange(USER_BASE_URL+"/id/" + userId + "/",
                        HttpMethod.DELETE, null, new ParameterizedTypeReference<Object>() {
                        });
    }

    @Override
    public BigInteger getObjectId() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<BigInteger> itemResponse =
                restTemplate.exchange(USER_BASE_URL+"/object/id",
                        HttpMethod.GET, null, new ParameterizedTypeReference<BigInteger>() {
                        });
        BigInteger result = itemResponse.getBody();
        return result;
    }

    @Override
    public void saveUserImage(BigInteger userId, String imageName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(imageName);
        restTemplate.exchange(USER_BASE_URL+"/id/" + userId + "/save/imageName/",
                HttpMethod.POST, request, new ParameterizedTypeReference<Object>() {
                });
    }

}
