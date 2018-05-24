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
    void removeItemById(BigInteger itemId);
    List<String> getAllCategories();
    void saveItem(Item item, String nameRu, String nameUk, String descriptionRu, String descriptionUk);
    void updateItem(Item item, String nameRu, String nameUk, String descriptionRu, String descriptionUk);
}
