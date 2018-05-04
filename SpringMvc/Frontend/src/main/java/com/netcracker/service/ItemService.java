package com.netcracker.service;

import com.netcracker.model.Item;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

public interface ItemService {
//    List<Item> getAllItems();
//    Item getItemById(BigInteger itemId);
//    List<Item> getItemsByCategory (String category);
    List<Item> getAllItems(Locale locale);
    Item getItemById(BigInteger itemId, Locale locale);
    List<Item> getItemsByCategory(String category, Locale locale);
    void removeItemById(BigInteger itemId);
    List<String>getAllCategories();
}
