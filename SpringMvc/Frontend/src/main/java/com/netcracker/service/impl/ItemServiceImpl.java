package com.netcracker.service.impl;

import com.netcracker.config.Constant;
import com.netcracker.model.Item;
import com.netcracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public List<Item> getAllItems(Locale locale) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Item>> itemResponse =
                restTemplate.exchange(Constant.BASE_URL_REST+"/"+ locale.getLanguage() +"/items/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Item>>() {
                        });
        List<Item> result = itemResponse.getBody();

        return result;
    }

    @Override
    public Item getItemById(BigInteger itemId, Locale locale) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Item> itemResponse =
                restTemplate.exchange(Constant.BASE_URL_REST+"/"+ locale.getLanguage() +"/items/" + itemId + "/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<Item>() {
                        });
        Item result = itemResponse.getBody();

        return result;
    }

    @Override
    public List<Item> getItemsByCategory(String category, Locale locale) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Item>> itemResponse =
                restTemplate.exchange(Constant.BASE_URL_REST+"/" + locale.getLanguage() + "/items/category/" + category,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Item>>() {
                        });
        List<Item> result = itemResponse.getBody();

        return result;
    }
//    public List<Item> getAllItems() {
//        return itemRepository.getAllItems();
//    }
//
//    @Override
//    public Item getItemById(BigInteger itemId) {
//        return itemRepository.getItemById(itemId);
//    }
//
//    @Override
//    public List<Item> getItemsByCategory(String category) {
//        return itemRepository.getItemsByCategory(category);
//    }
}
