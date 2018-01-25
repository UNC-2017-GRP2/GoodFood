package com.victoria.repository.impl;

import com.victoria.config.Constant;
import com.victoria.model.Item;
import com.victoria.repository.ItemRepository;
import com.victoria.config.Constant;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl extends AbstractRepositoryImpl implements ItemRepository {

    //private Connection connection;
    public ItemRepositoryImpl(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> result = new ArrayList<>();
        ResultSet resultSet;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OBJECTS);
            preparedStatement.setLong(1, Constant.ITEM_OBJ_TYPE_ID);
            resultSet = preparedStatement.executeQuery();
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
        String itemName = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OBJECT_BY_ID);
            preparedStatement.setObject(1, itemId, numericType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                itemName = resultSet.getString("NAME");
            }
            String itemDescription = null;
            String itemCategory = null;
            BigInteger itemCost = null;
            String itemImagePath = null;
            BigInteger itemImageId = null;
            BigInteger categoryId = null;
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_PARAMETERS);
            ps.setObject(1, itemId, numericType);
            ResultSet rs = ps.executeQuery();
            //идем по всем параметрам продукта
            while(rs.next()){
                long curAttrId = rs.getLong("ATTR_ID");

                if(curAttrId == Constant.ITEM_CATEGORY_ATTR_ID){
                    categoryId = new BigInteger(rs.getString("ENUM_VALUE"));
                    itemCategory = getNameValueById(categoryId, SQL_SELECT_ENUMS);
                }
                if(curAttrId == Constant.ITEM_DESCRIPTION_ATTR_ID){
                    itemDescription = rs.getString("TEXT_VALUE");
                }
                if (curAttrId == Constant.ITEMS_COST_ATTR_ID){
                    itemCost = new BigInteger(rs.getString("TEXT_VALUE"));
                }
                if (curAttrId == Constant.ITEM_IMAGE_ATTR_ID){
                    itemImageId = new BigInteger(rs.getString("REFERENCE_VALUE"));
                    itemImagePath = getNameValueById(itemImageId, SQL_SELECT_OBJECT_BY_ID);
                }
            }
            newItem = new Item(itemId,itemName,itemDescription,itemCategory,itemCost, itemImagePath,1);
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
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
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
            }
        }
        return result;
    }
    private List<Item>getList(List<Item>all, String category){
        List<Item> result = new ArrayList<>();
        for(Item item:all){
            if(item.getProductCategory().equals(category)){
                result.add(item);
            }
        }
        return result;
    }
}
