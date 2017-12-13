package com.victoria.service;

import com.victoria.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(long itemId);
}
