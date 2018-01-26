package com.netcracker.service;

import com.netcracker.model.Item;

import java.math.BigInteger;
import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(BigInteger itemId);
    List<Item> getItemsByCategory (String category);
}
