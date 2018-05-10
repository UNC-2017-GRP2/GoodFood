package com.netcracker.service.impl;

import com.netcracker.model.Item;
import com.netcracker.model.Order;
import com.netcracker.repository.ItemRepository;
import com.netcracker.service.ItemService;
import com.netcracker.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public List<Item> getAllItems(Locale locale) {
        List<Item> allItems;
        allItems = itemRepository.getAllItems();
        if (locale.equals(Locale.ENGLISH))
            return allItems;
        else {
            List<Item> result = new ArrayList<>();
            for (Item item : allItems) {
                result.add(itemRepository.getLocalizedItem(item, locale));
            }
            return result;
        }
    }

    @Override
    public Item getItemById(BigInteger itemId, Locale locale) {
        Item item = itemRepository.getItemById(itemId);
        if (locale.equals(Locale.ENGLISH)) {
            return item;
        }
        else {
            return itemRepository.getLocalizedItem(item, locale);
        }
    }

    @Override
    public List<Item> getItemsByCategory(String category, Locale locale) {
        List<Item> allItems = itemRepository.getItemsByCategory(category);
        if (locale.equals(Locale.ENGLISH))
            return allItems;
        else {
            List<Item> result = new ArrayList<>();
            for (Item item : allItems) {
                result.add(itemRepository.getLocalizedItem(item, locale));
            }
            return result;
        }
    }

    @Override
    public void removeItemById(BigInteger itemId) {
        itemRepository.removeItemById(itemId);
        for (Order order: orderService.getAllOrders(new Locale("en"))){
            boolean flag = false;
            for (Item item: order.getOrderItems()){
                if (item.getProductId().equals(itemId)){
                    flag = true;
                    break;
                }
            }
            if (flag){
                orderService.removeOrderById(order.getOrderId());
            }
        }
    }

    @Override
    public List<String> getAllCategories() {
        return itemRepository.getAllCategories();
    }

    @Override
    public void saveItem(Item item, String nameRu, String nameUk, String descriptionRu, String descriptionUk) {
        itemRepository.saveItem(item, nameRu, nameUk, descriptionRu, descriptionUk);
    }
//    public List<Item> getAllItems() {
//        return itemRepository.getAllItems();
//    }
//
//    @Override
//    public Item getItemById(BigInteger itemId) {
//        return itemRepository.getItemById(itemId);
//    }
//
//    @Override
//    public List<Item> getItemsByCategory(String category) {
//        return itemRepository.getItemsByCategory(category);
//    }
}
