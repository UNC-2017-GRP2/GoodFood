package com.netcracker.service.impl;

import com.netcracker.model.Item;
import com.netcracker.repository.ItemRepository;
import com.netcracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.getAllItems();
    }

    @Override
    public Item getItemById(BigInteger itemId) {
        return itemRepository.getItemById(itemId);
    }

    @Override
    public List<Item> getItemsByCategory(String category) {
        return itemRepository.getItemsByCategory(category);
    }
}
