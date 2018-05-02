package com.netcracker.repository.impl;

import com.netcracker.config.Constant;
import com.netcracker.model.Item;
import com.netcracker.repository.ItemRepository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemRepositoryImpl extends AbstractRepositoryImpl implements ItemRepository {

    public ItemRepositoryImpl(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> result = new ArrayList<>();
        try{
            ResultSet resultSet = getObjectsByObjectTypeId(Constant.ITEM_OBJ_TYPE_ID);
            //идем по всем продуктам
            while (resultSet.next()){
                String itemId = resultSet.getString("OBJECT_ID");
                Item newItem = getItemById(new BigInteger(itemId));
                result.add(newItem);
            }
            //}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Item getItemById(BigInteger itemId) {
        Item newItem = null;
        try {
            String itemName = null;
            String itemDescription = null;
            String itemCategory = null;
            BigInteger itemCost = null;
            String itemImagePath = null;
            BigInteger itemImageId = null;
            BigInteger categoryId = null;
            ResultSet resultSet = getParametersByObjectId(itemId);
            //идем по всем параметрам продукта
            while(resultSet.next()){
                long curAttrId = resultSet.getLong("ATTR_ID");

                if(curAttrId == Constant.ITEM_CATEGORY_ATTR_ID){
                    categoryId = new BigInteger(resultSet.getString("ENUM_VALUE"));
                    itemCategory = getNameValueById(categoryId, Constant.SQL_SELECT_ENUMS);
                }
                if(curAttrId == Constant.ITEM_DESCRIPTION_ATTR_ID){
                    itemDescription = resultSet.getString("TEXT_VALUE");
                }
                if (curAttrId == Constant.ITEMS_COST_ATTR_ID){
                    itemCost = new BigInteger(resultSet.getString("TEXT_VALUE"));
                }
                if (curAttrId == Constant.NAME_ATTR_ID) {
                    itemName = resultSet.getString("TEXT_VALUE");
                }
                if (curAttrId == Constant.ITEM_IMAGE_ATTR_ID){
                    itemImageId = new BigInteger(resultSet.getString("REFERENCE_VALUE"));
                    itemImagePath = getNameValueById(itemImageId, Constant.SQL_SELECT_OBJECT_BY_ID);
                }
            }
            newItem = new Item(itemId,itemName,itemDescription,itemCategory,itemCost, itemImagePath,1);
            if (resultSet != null){
                resultSet.close();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return newItem;
    }

    private String getNameValueById(BigInteger id, String sql){
        String result = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id, numericType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result = resultSet.getString("NAME");
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Item> getItemsByCategory(String category) {
        List<Item>result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_OBJ_ID_BY_ATTR_AND_ENUM);
            preparedStatement.setLong(1, Constant.ITEM_CATEGORY_ATTR_ID);
            preparedStatement.setLong(2, Long.valueOf(category));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result.add(getItemById(new BigInteger(resultSet.getString("OBJECT_ID"))));
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }


    /*@Override
    public List<Item> getItemsByCategory(String category) {
        List<Item> all = getAllItems();
        List<Item> result = new ArrayList<>();
        if (category == null){
            result = getList(all,"Pizza");
        }else{
            switch (category){
                case "Pizza":
                    result = getList(all,"Pizza");
                    break;
                case "Sushi":
                    result = getList(all,"Sushi");
                    break;
                case "Burgers":
                    result = getList(all,"Burgers");
                    break;
                case "Salads":
                    result = getList(all,"Salads");
                    break;
                case "Snacks":
                    result = getList(all,"Snacks");
                    break;
                case "Dessert":
                    result = getList(all,"Dessert");
                    break;
                case "Beverages":
                    result = getList(all,"Beverages");
                    break;
                case "Alcohol":
                    result = getList(all,"Alcohol");
                    break;
                default:
                    result = getList(all,"Pizza");
                    break;
            }
        }
        return result;
    }*/
    private List<Item> getList(List<Item>all, String category){
        List<Item> result = new ArrayList<>();
        for(Item item:all){
            if(item.getProductCategory().equals(category)){
                result.add(item);
            }
        }
        return result;
    }

    public Item getLocalizedItem(Item item, Locale locale) {
        long langId;
        if (locale.toString().equals("ru")) {
            langId = Constant.LANG_RUSSIAN;
        }
        else if (locale.toString().equals("uk")) {
            langId = Constant.LANG_UKRAINIAN;
        }
        else {
            return item;
        }
        String itemName = item.getProductName();
        String itemDescription = item.getProductDescription();
        try {
            ResultSet resultSet = getLocStringsByObjectId(item.getProductId(), langId);
            while(resultSet.next()){
                long curAttrId = resultSet.getLong("ATTR_ID");

                if(curAttrId == Constant.ITEM_DESCRIPTION_ATTR_ID){
                    itemDescription = resultSet.getString("LOC_TEXT_VALUE");
                }
                if (curAttrId == Constant.NAME_ATTR_ID) {
                    itemName = resultSet.getString("LOC_TEXT_VALUE");
                }
            }
            item.setProductName(itemName);
            item.setProductDescription(itemDescription);
            if (resultSet != null){
                resultSet.close();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return item;
    }

    @Override
    public void removeItemById(BigInteger itemId){
        removeObjectById(itemId);
    }

    @Override
    public List<String> getAllCategories() {
        List<String>result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_ENUMS_BY_TYPE_ID);
            preparedStatement.setLong(1, Constant.ITEM_CATEGORY_ENUM_TYPE_ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result.add(resultSet.getString("NAME"));
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
}
