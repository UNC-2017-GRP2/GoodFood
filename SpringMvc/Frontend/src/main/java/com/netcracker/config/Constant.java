package com.netcracker.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constant {

    public static Map<String, Long> ROLES;
    static {
        Map<String, Long> map = new HashMap<>();
        map.put("ROLE_ADMIN", Constant.ROLE_ADMIN_ENUM_ID);
        map.put("ROLE_USER", Constant.ROLE_USER_ENUM_ID);
        map.put("ROLE_COURIER", Constant.ROLE_COURIER_ENUM_ID);
        ROLES = Collections.unmodifiableMap(map);
    }

    /*public static Map<String, Long> STATUSES;
    static {
        Map<String, Long> map = new HashMap<>();
        map.put("Created", Constant.STATUS_CREATED_ENUM_ID);
        map.put("Linked with courier", Constant.STATUS_LINKED_WITH_COURIER_ENUM_ID);
        map.put("Delivered", Constant.STATUS_DELIVERED_ENUM_ID);
        map.put("Expired", Constant.STATUS_EXPIRED_ENUM_ID);
        map.put("Cancelled", Constant.STATUS_CANCELLED_ENUM_ID);
        STATUSES = Collections.unmodifiableMap(map);
    }*/
    public static Map<Long, String> STATUSES;
    static {
        Map<Long, String> map = new HashMap<>();
        map.put(Constant.STATUS_CREATED_ENUM_ID, "Created");
        map.put(Constant.STATUS_LINKED_WITH_COURIER_ENUM_ID, "Linked with courier");
        map.put(Constant.STATUS_DELIVERED_ENUM_ID, "Delivered");
        map.put(Constant.STATUS_EXPIRED_ENUM_ID, "Expired");
        map.put(Constant.STATUS_CANCELLED_ENUM_ID, "Cancelled");
        STATUSES = Collections.unmodifiableMap(map);
    }



    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_COURIER = "ROLE_COURIER";
    public static final String ROLE_USER = "ROLE_USER";

    public static final long START_EXPIRATION_TIME = 3;
    public static final long END_EXPIRATION_TIME = 12;
    public static final int ORDERS_QUANTITY_ON_PAGE = 4;

    /**Languages**/
    public static final long LANG_RUSSIAN = 81;
    public static final long LANG_UKRAINIAN = 82;


    /**Table attr_types**/
    public static final long TEXT_ATTR_TYPE_ID = 51;
    public static final long NUMBER_ATTR_TYPE_ID = 52;
    public static final long REFERENCE_ATTR_TYPE_ID = 53;
    public static final long DATE_ATTR_TYPE_ID = 54;
    public static final long ENUM_VALUE_ATTR_TYPE_ID = 55;
    public static final long POINT_VALUE_ATTR_TYPE_ID = 56;

    /**Table attributes**/
    public static final long FULL_NAME_ATTR_ID = 400;
    public static final long NAME_ATTR_ID = 401;
    public static final long PASSWORD_HASH_ATTR_ID = 402;
    public static final long PHONE_NUMBER_ATTR_ID = 403;
    public static final long BIRTHDAY_ATTR_ID = 404;
    public static final long USER_ROLE_ATTR_ID = 405;
    public static final long EMAIL_ATTR_ID = 406;
    public static final long ORDERS_COST_ATTR_ID = 407;
    public static final long ITEM_CATEGORY_ATTR_ID = 408;
    public static final long ITEMS_COST_ATTR_ID = 409;
    public static final long ADDRESS_ATTR_ID = 410;
    public static final long ORDER_PAID_ATTR_ID = 411;
    public static final long ORDER_ATTR_ID = 412;
    public static final long ITEM_DESCRIPTION_ATTR_ID = 414;
    public static final long ITEM_ATTR_ID = 415;
    public static final long STATUS_ATTR_ID = 417;
    public static final long COURIER_ATTR_ID = 416;
    public static final long ITEM_IMAGE_ATTR_ID = 418;
    public static final long ORDER_PAYMENT_TYPE_ATTR_ID = 419;
    public static final long ORDER_CREATION_DATE_ATTR_ID = 421;

    /**Table enum_types**/
    public static final long USER_ROLE_ENUM_TYPE_ID = 700;
    public static final long ORDER_STATE_ENUM_TYPE_ID = 701;
    public static final long ITEM_CATEGORY_ENUM_TYPE_ID = 702;
    public static final long ORDER_PAYMENT_TYPE_ENUM_TYPE_ID = 703;

    /**Table enums**/
    public static final long ROLE_ADMIN_ENUM_ID = 800;
    public static final long ROLE_USER_ENUM_ID = 801;
    public static final long ROLE_COURIER_ENUM_ID = 802;
    public static final long STATUS_CREATED_ENUM_ID = 803;
    public static final long STATUS_LINKED_WITH_COURIER_ENUM_ID = 805;
    public static final long STATUS_DELIVERED_ENUM_ID = 806;
    public static final long STATUS_EXPIRED_ENUM_ID = 808;
    public static final long STATUS_CANCELLED_ENUM_ID = 809;
    public static final long CATEGORY_PIZZA = 811;
    public static final long CATEGORY_SUSHI = 812;
    public static final long CATEGORY_BURGERS = 813;
    public static final long CATEGORY_SALADS = 814;
    public static final long CATEGORY_SNACKS = 815;
    public static final long CATEGORY_DESSERT = 816;
    public static final long CATEGORY_BEVERAGES = 817;
    public static final long CATEGORY_ALCOHOL = 820;
    public static final long CASH_PAYMENT_ENUM_ID = 818;
    public static final long PAYMENT_BY_CARD_ENUM_ID = 819;

    /*public static final long PIZZA_ENUM_ID = 811;
    public static final long SUSHI_ENUM_ID = 812;
    public static final long BURGERS_ENUM_ID = 813;
    public static final long SALADS_ENUM_ID = 814;
    public static final long SNACKS_ENUM_ID = 815;
    public static final long DESSERT_ENUM_ID = 816;
    public static final long BEVERAGES_ENUM_ID = 817;*/


    /**Table object_types**/
    public static final long USER_OBJ_TYPE_ID = 300;
    public static final long IMAGE_OBJ_TYPE_ID = 301;
    public static final long ORDER_OBJ_TYPE_ID = 305;
    public static final long ITEM_OBJ_TYPE_ID = 306;

    /**SQL QUERIES**/

    public static final String SQL_SELECT_ID = "select id_generator()";
    public static final String SQL_SELECT_OBJECT_BY_ID = "select * from \"OBJECTS\" where \"OBJECT_ID\" = ?";
    public static final String SQL_SELECT_OBJECTS = "select * from \"OBJECTS\" where \"OBJECT_TYPE_ID\" = ?";
    public static final String SQL_SELECT_OBJECT_ID_BY_NAME = "select \"OBJECT_ID\" from \"OBJECTS\" where \"NAME\" = ?";
    public static final String SQL_SELECT_OBJECT_ID_BY_NAME_AND_TYPE = "select \"OBJECT_ID\" from \"OBJECTS\" where \"NAME\" = ? and \"OBJECT_TYPE_ID\" = ?";
    public static final String SQL_SELECT_NAME_BY_OBJECT_ID = "select \"NAME\" from \"OBJECTS\" where \"OBJECT_ID\" = ?";
    public static final String SQL_SELECT_ATTRS_OBJECT_TYPE_ID = "select \"ATTR_ID\" from \"ATTR_OBJECT_TYPES\" where \"OBJECT_TYPE_ID\" = ?";
    public static final String SQL_SELECT_ATTR_TYPE_ID = "select \"ATTR_TYPE_ID\" from \"ATTRIBUTES\" where \"ATTR_ID\" = ?";

    public static final String SQL_SELECT_PARAMETERS_BY_OBJ_ATTR = "select * from \"PARAMETERS\" where \"OBJECT_ID\" = ? and \"ATTR_ID\" = ?";
    public static final String SQL_SELECT_PARAMETERS_BY_OBJECT_ID = "select * from \"PARAMETERS\" where \"OBJECT_ID\" = ?";
    public static final String SQL_SELECT_LOC_STRINGS_BY_OBJECT_ID = "select * from \"LOC_STRINGS\" where \"OBJECT_ID\" = ? and \"LANG_ID\" = ?";
    public static final String SQL_SELECT_OBJECT_ID_BY_REFERENCE_VAL = "select \"OBJECT_ID\" from \"PARAMETERS\" where \"REFERENCE_VALUE\" = ?";
    public static final String SQL_SELECT_OBJECT_ID_BY_TEXT_VAL_AND_ATTR_ID = "select \"OBJECT_ID\" from \"PARAMETERS\" where \"ATTR_ID\" = ? and \"TEXT_VALUE\" = ?";
    public static final String SQL_SELECT_REFERENCE_VAL_BY_OBJ_ID_AND_ATTR_ID = "select \"REFERENCE_VALUE\" from \"PARAMETERS\" where \"OBJECT_ID\" = ? and \"ATTR_ID\" = ?";
    public static final String SQL_SELECT_TEXT_VAL_BY_OBJ_ID_AND_ATTR_ID = "select \"TEXT_VALUE\" from \"PARAMETERS\" where \"ATTR_ID\" = ? and \"OBJECT_ID\" = ? ";
    public static final String SQL_SELECT_OBJ_ID_BY_ATTR_AND_ENUM = "select \"OBJECT_ID\" from \"PARAMETERS\" where \"ATTR_ID\" = ? and \"ENUM_VALUE\" = ?";

    public static final String SQL_SELECT_ENUMS_BY_TYPE_ID = "select * from \"ENUMS\" where \"ENUM_TYPE_ID\" = ?";
    public static final String SQL_SELECT_ENUMS = "select * from \"ENUMS\" where \"ENUM_ID\" = ?";
    public static final String SQL_SELECT_ENUM_NAME_BY_ID = "select \"NAME\" from \"ENUMS\" where \"ENUM_ID\" = ?";
    public static final String SQL_SELECT_ENUM_ID_BY_ENUM_VALUE = "select \"ENUM_ID\" from \"ENUMS\" where \"NAME\" = ?";
    public static final String SQL_SELECT_ROLE_USER_ENUM_ID = "select \"ENUM_ID\" from \"ENUMS\" where \"NAME\" = \'ROLE_USER\'";

    public static final String SQL_INSERT_INTO_OBJECTS = "insert into \"OBJECTS\" (\"NAME\",\"OBJECT_ID\", \"PARENT_ID\", \"OBJECT_TYPE_ID\") values(?,?,?,?)";
    //public static final String SQL_INSERT_INTO_PARAMETERS = "insert into \"PARAMETERS\" (\"OBJECT_ID\",\"ATTR_ID\", \"TEXT_VALUE\", \"DATE_VALUE\", \"REFERENCE_VALUE\", \"ENUM_VALUE\", \"POINT_VALUE\") values(?,?,?,?,?,?,?)";
    public static final String SQL_INSERT_INTO_PARAMETERS = "insert into \"PARAMETERS\" (\"OBJECT_ID\",\"ATTR_ID\", \"TEXT_VALUE\", \"DATE_VALUE\", \"REFERENCE_VALUE\", \"ENUM_VALUE\", \"POINT_VALUE\") values(?,?,?,?,?,?,point(?,?))";

    public static final String SQL_UPDATE_OBJECT = "UPDATE \"OBJECTS\" SET \"NAME\"=?, \"OBJECT_ID\"=?, \"PARENT_ID\"=?, \"OBJECT_TYPE_ID\"=? WHERE \"OBJECT_ID\"=? and \"NAME\"=? ";
    public static final String SQL_UPDATE_OBJECT_NAME = "UPDATE \"OBJECTS\" SET \"NAME\"=? WHERE \"OBJECT_ID\"=?";
    public static final String SQL_UPDATE_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"OBJECT_ID\"=?, \"ATTR_ID\"=?, \"TEXT_VALUE\"=?, \"DATE_VALUE\"=?, \"REFERENCE_VALUE\"=?, \"ENUM_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    public static final String SQL_UPDATE_TEXT_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"TEXT_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    public static final String SQL_UPDATE_DATE_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"DATE_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    public static final String SQL_UPDATE_REFERENCE_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"REFERENCE_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    public static final String SQL_UPDATE_ENUM_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"ENUM_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    public static final String SQL_UPDATE_POINT_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"POINT_VALUE\"=point(?,?) WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    //public static final String SQL_UPDATE_NULL_POINT_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"POINT_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    public static final String SQL_DELETE_FROM_PARAMETERS = "delete from \"PARAMETERS\" where \"OBJECT_ID\" = ? and \"ATTR_ID\" = ?";
    public static final String SQL_DELETE_ALL_PARAMETERS_BY_OBJ_ID = "delete from \"PARAMETERS\" where \"OBJECT_ID\" = ?";
    public static final String SQL_DELETE_OBJECT = "delete from \"OBJECTS\" where \"OBJECT_ID\" = ?";

    public static final String BASE_URL_REST = "http://127.0.0.1:8080/rest";
}