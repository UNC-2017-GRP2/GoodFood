package com.victoria.repository;

import com.victoria.model.Item;

import java.util.ArrayList;

public interface OrderRepository {
    void checkout(ArrayList<Item> items, String username);
}
