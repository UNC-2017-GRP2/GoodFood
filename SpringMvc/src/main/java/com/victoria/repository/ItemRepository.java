package com.victoria.repository;

import com.victoria.model.Item;

import java.math.BigInteger;
import java.util.List;

public interface ItemRepository {
    List<Item> getAllItems();
    Item getItemById(BigInteger itemId);

}
