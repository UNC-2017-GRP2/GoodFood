package com.victoria.repository;

import com.victoria.model.Item;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public interface ItemRepository {
    List<Item> getAllItems();
    Item getItemById(BigInteger itemId);
    List<Item> getItemsByCategory (String category);
}
