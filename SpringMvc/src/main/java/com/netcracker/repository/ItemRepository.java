package com.netcracker.repository;

import com.netcracker.model.Item;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

public interface ItemRepository {
    List<Item> getAllItems();
    Item getItemById(BigInteger itemId);
    List<Item> getItemsByCategory (String category);
    Item getLocalizedItem(Item item, Locale locale);
}
