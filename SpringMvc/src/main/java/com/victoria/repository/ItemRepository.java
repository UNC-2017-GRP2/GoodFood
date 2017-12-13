package com.victoria.repository;

import com.victoria.model.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> getAllItems();
    Item getItemById(long itemId);

}
