package com.victoria.repository.impl;

import com.victoria.model.Item;
import com.victoria.repository.ItemRepository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {

    private Connection connection;
    public ItemRepositoryImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    private String SQL_SELECT_PARAMETERS = "select * from \"PARAMETERS\" where \"OBJECT_ID\" = ?";
    private String SQL_SELECT_OBJECTS = "select * from \"OBJECTS\" where \"OBJECT_TYPE_ID\" = ?";
    private String SQL_SELECT_OBJECT_BY_ID = "select * from \"OBJECTS\" where \"OBJECT_ID\" = ?";
    private String SQL_SELECT_ITEM_OBJ_TYPE_ID = "select \"OBJECT_TYPE_ID\" from \"OBJECT_TYPES\" where \"NAME\" = \'Продукт\'";

    private String SQL_SELECT_CATEGORY_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Категория продукта\'";
    private String SQL_SELECT_COST_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Стоимость продукта\'";
    private String SQL_SELECT_DESCRIPTION_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Описание продукта\'";

    @Override
    public List<Item> getAllItems() {
        /*long categoryAttrId = getAttrId(SQL_SELECT_CATEGORY_ATTR_ID);
        long costAttrId = getAttrId(SQL_SELECT_COST_ATTR_ID);
        long descriptionAttrId = getAttrId(SQL_SELECT_DESCRIPTION_ATTR_ID);*/
        List<Item> result = new ArrayList<>();
        long itemObjTypeId = 0;
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ITEM_OBJ_TYPE_ID);
            while (resultSet.next()){
                itemObjTypeId = resultSet.getLong("OBJECT_TYPE_ID");
            }
            statement.close();
            resultSet.close();

            if(itemObjTypeId != 0){
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OBJECTS);
                preparedStatement.setLong(1,itemObjTypeId);
                resultSet = preparedStatement.executeQuery();

                //идем по всем продуктам
                while (resultSet.next()){
                    long itemId = resultSet.getLong("OBJECT_ID");
                    Item newItem = getItemById(itemId);
                    /*String itemName = resultSet.getString("NAME");
                    String itemDescription = null;
                    String itemCategory = null;
                    BigDecimal itemCost = null;
                    PreparedStatement ps = connection.prepareStatement(SQL_SELECT_PARAMETERS);
                    ps.setLong(1,itemId);
                    ResultSet rs = ps.executeQuery();

                    //идем по всем параметрам каждого продукта
                    while(rs.next()){
                        long curAttrId = rs.getLong("ATTR_ID");

                        if(curAttrId == categoryAttrId){
                            itemCategory = rs.getString("TEXT_VALUE");
                        }
                        if(curAttrId == descriptionAttrId){
                            itemDescription = rs.getString("TEXT_VALUE");
                        }
                        if (curAttrId == costAttrId){
                            itemCost = new BigDecimal(rs.getString("TEXT_VALUE"));
                        }
                    }
                    Item newItem = new Item(itemId,itemName,itemDescription,itemCategory,itemCost);*/
                    result.add(newItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Item getItemById(long itemId) {
        Item newItem = null;
        long categoryAttrId = getAttrId(SQL_SELECT_CATEGORY_ATTR_ID);
        long costAttrId = getAttrId(SQL_SELECT_COST_ATTR_ID);
        long descriptionAttrId = getAttrId(SQL_SELECT_DESCRIPTION_ATTR_ID);
        String itemName = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OBJECT_BY_ID);
            preparedStatement.setLong(1,itemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                itemName = resultSet.getString("NAME");
            }
            String itemDescription = null;
            String itemCategory = null;
            BigDecimal itemCost = null;
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_PARAMETERS);
            ps.setLong(1,itemId);
            ResultSet rs = ps.executeQuery();
            //идем по всем параметрам продукта
            while(rs.next()){
                long curAttrId = rs.getLong("ATTR_ID");

                if(curAttrId == categoryAttrId){
                    itemCategory = rs.getString("TEXT_VALUE");
                }
                if(curAttrId == descriptionAttrId){
                    itemDescription = rs.getString("TEXT_VALUE");
                }
                if (curAttrId == costAttrId){
                    itemCost = new BigDecimal(rs.getString("TEXT_VALUE"));
                }
            }
            newItem = new Item(itemId,itemName,itemDescription,itemCategory,itemCost);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return newItem;
    }

    private long getAttrId(String sql){
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()) {
                return resultSet.getLong("ATTR_ID");
            }
        }catch (Exception e){
            System.out.println(e.getMessage() + "LOOOOOOOOOOOOOOOOL4");
        }
        return 0;
    }
}
