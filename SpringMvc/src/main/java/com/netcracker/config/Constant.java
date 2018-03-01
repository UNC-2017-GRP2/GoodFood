package com.netcracker.config;

public class Constant {

    public static final long START_EXPIRATION_TIME = 3;
    public static final long END_EXPIRATION_TIME = 12;

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
    public static final long USERNAME_ATTR_ID = 401;
    public static final long PASSWORD_HASH_ATTR_ID = 402;
    public static final long PHONE_NUMBER_ATTR_ID = 403;
    public static final long BIRTHDAY_ATTR_ID = 404;
    public static final long USER_ROLE_ATTR_ID = 405;
    public static final long EMAIL_ATTR_ID = 406;
    public static final long ORDERS_COST_ATTR_ID = 407;
    public static final long ITEM_CATEGORY_ATTR_ID = 408;
    public static final long ITEMS_COST_ATTR_ID = 409;
    public static final long ADDRESS_ATTR_ID = 410;
    //public static final long BANK_CARD_NUMBER_ATTR_ID = 411;
    public static final long ORDER_ATTR_ID = 412;
    public static final long ITEM_NAME_ATTR_ID = 413;
    public static final long ITEM_DESCRIPTION_ATTR_ID = 414;
    public static final long ITEM_ATTR_ID = 415;
    public static final long STATUS_ATTR_ID = 417;
    public static final long COURIER_ATTR_ID = 416;
    public static final long ITEM_IMAGE_ATTR_ID = 418;
    public static final long ORDER_ADDRESS_ATTR_ID = 419;
    public static final long ORDER_PHONE_ATTR_ID = 420;
    public static final long ORDER_CREATION_DATE_ATTR_ID = 421;

    /**Table enum_types**/
    public static final long USER_ROLE_ENUM_TYPE_ID = 700;
    public static final long ORDER_STATE_ENUM_TYPE_ID = 701;
    public static final long ITEM_CATEGORY_ENUM_TYPE_ID = 702;

    /**Table enums**/
    public static final long ROLE_ADMIN_ENUM_ID = 800;
    public static final long ROLE_USER_ENUM_ID = 801;
    public static final long ROLE_COURIER_ENUM_ID = 802;
    public static final long STATUS_CREATED_ENUM_ID = 803;
    public static final long STATUS_WITHOUT_COURIER_ENUM_ID = 804;
    public static final long STATUS_LINKED_WITH_COURIER_ENUM_ID = 805;
    public static final long STATUS_DELIVERED_ENUM_ID = 806;
    public static final long STATUS_DELIVERY_CONFIRMED_ENUM_ID = 807;
    public static final long STATUS_EXPIRED_ENUM_ID = 808;
    public static final long STATUS_CANCELLED_ENUM_ID = 809;
    public static final long STATUS_PAID_ENUM_ID = 810;
    public static final long PIZZA_ENUM_ID = 811;
    public static final long SUSHI_ENUM_ID = 812;
    public static final long BURGERS_ENUM_ID = 813;
    public static final long SALADS_ENUM_ID = 814;
    public static final long SNACKS_ENUM_ID = 815;
    public static final long DESSERT_ENUM_ID = 816;
    public static final long BEVERAGES_ENUM_ID = 817;


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

    public static final String SQL_SELECT_ENUMS = "select * from \"ENUMS\" where \"ENUM_ID\" = ?";
    public static final String SQL_SELECT_ENUM_NAME_BY_ID = "select \"NAME\" from \"ENUMS\" where \"ENUM_ID\" = ?";
    public static final String SQL_SELECT_ENUM_ID_BY_ENUM_VALUE = "select \"ENUM_ID\" from \"ENUMS\" where \"NAME\" = ?";
    public static final String SQL_SELECT_ROLE_USER_ENUM_ID = "select \"ENUM_ID\" from \"ENUMS\" where \"NAME\" = \'ROLE_USER\'";

    public static final String SQL_INSERT_INTO_OBJECTS = "insert into \"OBJECTS\" (\"NAME\",\"OBJECT_ID\", \"PARENT_ID\", \"OBJECT_TYPE_ID\") values(?,?,?,?)";
    public static final String SQL_INSERT_INTO_PARAMETERS = "insert into \"PARAMETERS\" (\"OBJECT_ID\",\"ATTR_ID\", \"TEXT_VALUE\", \"DATE_VALUE\", \"REFERENCE_VALUE\", \"ENUM_VALUE\", \"POINT_VALUE\") values(?,?,?,?,?,?,?)";
    public static final String SQL_INSERT_INTO_PARAMETERS_POINT_VALUE = "insert into \"PARAMETERS\" (\"OBJECT_ID\",\"ATTR_ID\", \"TEXT_VALUE\", \"DATE_VALUE\", \"REFERENCE_VALUE\", \"ENUM_VALUE\", \"POINT_VALUE\") values(?,?,?,?,?,?,point(?,?))";

    public static final String SQL_UPDATE_OBJECT = "UPDATE \"OBJECTS\" SET \"NAME\"=?, \"OBJECT_ID\"=?, \"PARENT_ID\"=?, \"OBJECT_TYPE_ID\"=? WHERE \"OBJECT_ID\"=? and \"NAME\"=? ";
    public static final String SQL_UPDATE_OBJECT_NAME = "UPDATE \"OBJECTS\" SET \"NAME\"=? WHERE \"OBJECT_ID\"=?";
    public static final String SQL_UPDATE_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"OBJECT_ID\"=?, \"ATTR_ID\"=?, \"TEXT_VALUE\"=?, \"DATE_VALUE\"=?, \"REFERENCE_VALUE\"=?, \"ENUM_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    public static final String SQL_UPDATE_TEXT_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"TEXT_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    public static final String SQL_UPDATE_DATE_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"DATE_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    public static final String SQL_UPDATE_REFERENCE_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"REFERENCE_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    public static final String SQL_UPDATE_ENUM_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"ENUM_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    public static final String SQL_UPDATE_POINT_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"POINT_VALUE\"=(?,?) WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    public static final String SQL_UPDATE_NULL_POINT_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"POINT_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";


    public static final String SQL_DELETE_FROM_PARAMETERS = "delete from \"PARAMETERS\" where \"OBJECT_ID\" = ? and \"ATTR_ID\" = ?";
}
