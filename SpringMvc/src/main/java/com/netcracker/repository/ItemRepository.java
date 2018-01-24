package com.netcracker.repository;

import com.netcracker.model.Item;

import java.math.BigInteger;
import java.util.List;

public interface ItemRepository {
    List<Item> getAllItems();
    Item getItemById(BigInteger itemId);
    List<Item> getItemsByCategory (String category);
}
