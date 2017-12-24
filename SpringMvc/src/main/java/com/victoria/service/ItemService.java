package com.victoria.service;

import com.victoria.model.Item;

import java.math.BigInteger;
import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(BigInteger itemId);
}
