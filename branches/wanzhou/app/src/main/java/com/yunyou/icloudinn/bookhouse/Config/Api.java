package com.yunyou.icloudinn.bookhouse.Config;

public class Api {

    public static String BASE_URL="http://qz.icloudinn.com";// 应用系统
    public static String BASE_USER="http://users.icloudinn.com";// 用户系统

    public static String BOOK_HOT_LIST =BASE_URL+"/api/book?type=hot";
    public static String BOOK_DETAIL =BASE_URL+"/api/book/";
    public static String HOUSE_LIST =BASE_URL+"/api/house?type=";
    public static String HOUSE_DETAIL=BASE_URL+"/api/house/";
    public static String PRICE_CALENDAR=BASE_URL+"/api/price_calendar?hotel_id=";
    public static String CODE_LOGIN=BASE_USER+"/Api/User/smsLogin";
    public static String SEND_CODE=BASE_USER+"/Api/User/sendsms";
    public static String ORDER =BASE_URL+"/api/hotel_order";
    public static String COLLECT=BASE_URL+"/api/collect";
    public static String REGISTER=BASE_USER+"/Api/User/smsRegister";
    public static String PRAISE=BASE_URL+"/api/comment/praise";
    public static String COMMENT_PRAISE=BASE_URL+"/api/dynamic/praise";
    public static String COMMENT=BASE_URL+"/api/comment";
    public static String REPLY=BASE_URL+"/api/comment/reply";
    public static String BOOK_WORM=BASE_URL+"/api/user/bookworm";
    public static String WRITE_BOOK = BASE_URL+"/api/book_write";
    public static String WRITE_BOOK_FIND = BASE_URL+"/api/book_write_chapter/find";
    public static String CIRCLE_DYNAMIC=BASE_URL+"/api/dynamic";
    public static String RENT_BOOK=BASE_URL+"/api/book_rent";
    public static String DONATE_BOOK=BASE_URL+"/api/book_donate";
    public static String COLLECT_BOOK=BASE_URL+"/api/collect?type=2";
    public static String COLLECT_HOUSE=BASE_URL+"/api/collect?type=1";
    public static String ATTENTION_TO=BASE_URL+"/api/user/concern?type=0";
    public static String TO_ATTENTION=BASE_URL+"/api/user/concern?type=1";
    public static String USER_LIST=BASE_URL+"/api/user";
    public static String CHECK_OUT=BASE_URL+"/api/hotel_order/checkOut/";
    public static String BOOK_TYPE=BASE_URL+"/api/category?resource_type=2";
    public static String BOOK_CATEGORY=BASE_URL+"/api/book/category";
    public static String MY_BOOK=BASE_URL+"/api/dynamic/mybook";
    public static String BOOK=BASE_URL+"/api/book/";
    public static String BIND=BASE_USER+"/Api/User/bindingPhone";
    public static String ISMENBER=BASE_URL+"/api/user/isMember";
    public static String ATTENTION=BASE_USER+"/api/concern/";//圈子关注列表API,需要提交参数type；
    public static String UPLOAD_PICTURE=BASE_URL+"/api/heart_feeling/upload";
    public static String UPLOAD_MOOD=BASE_URL+"/api/heart_feeling";
    public static String USER_DETAIL=BASE_URL+"/api/user/";
    public static String CANCEL_ORDER=BASE_URL+"/api/hotel_order/cancel/";
    public static String WX_LOGIN=BASE_USER+"/Api/User/wxLogin";
    public static String IM_USERSIG=BASE_URL+"/api/signature/im";//IM_SDK聊天签名API
}
