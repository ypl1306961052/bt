package com.yunyou.icloudinn.bookhouse.Util;

import com.alibaba.fastjson.JSON;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;


public class UserUtils {
    private static final String KEY_USER ="user";
    private static final String KEY_TOKEN ="token";

    public static void saveUser(UserData user){
        SPUtils spUtils =new SPUtils(KEY_USER);

        if(user==null){
            spUtils.put(KEY_USER,"");
        }else{
            String userStr = JSON.toJSONString(user);
            spUtils.put(KEY_USER,userStr);
        }
    }
    public static UserData getUser( ){
        SPUtils spUtils =new SPUtils(KEY_USER);
        String userStr =  spUtils.getString(KEY_USER);
        if (null ==userStr ||"".equals(userStr)) {
            return null;
        }
        return (UserData) JSON.parseObject(userStr,UserData.class);
    }
    public static void saveToken (String token){
        SPUtils spUtils =new SPUtils(KEY_TOKEN);
        if(token==null){
            spUtils.remove(KEY_TOKEN);
        }else{
            spUtils.put(KEY_TOKEN,token);
        }
    }
    public static String getToken(){
        SPUtils spUtils =new SPUtils(KEY_TOKEN);
        String userStr =  spUtils.getString( KEY_TOKEN);
        if (null ==userStr || "".equals(userStr)) {
            return null;
        }
        return userStr;
    }

}
