package com.netcracker.config;

public class Constant {

    public static final long START_EXPIRATION_TIME = 3;
    public static final long END_EXPIRATION_TIME = 12;

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
}
