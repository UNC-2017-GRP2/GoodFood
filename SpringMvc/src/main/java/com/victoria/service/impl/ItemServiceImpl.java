package com.victoria.service.impl;

import com.victoria.model.Item;
import com.victoria.repository.ItemRepository;
import com.victoria.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Item getItemById(long itemId) {
        return itemRepository.getItemById(itemId);
    }
}
