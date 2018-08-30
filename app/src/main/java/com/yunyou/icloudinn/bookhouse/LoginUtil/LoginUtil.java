package com.yunyou.icloudinn.bookhouse.LoginUtil;


import android.content.Context;
import android.content.SharedPreferences;

import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;

/**
 * Created by lijunyu on 2017/3/23
 */
public class LoginUtil {

    /**
     *
     * @param input 用户的输入
     * @return 相应的返回码
     * 0:用户输入的是数字
     * 1:用户输入为空
     * 2:用户输入的不是数字
     */
    public static int onlyNumber(String input){
        if (input.isEmpty()){
            ToastUtils.showLongToast("请输入出行人数");
            return 1;
        }else {
            if (RegexUtils.isNumber(input)){
                return 0;
            }else {
                ToastUtils.showLongToast("请输入数字");
                return 2;
            }
        }
    }
    /**
     * @param platformCode:平台码
     * @return 相应的返回码
     * 0:
     * 1:
     * 2:
     * 3:
     */
    public int thirdPartyLogin(int platformCode){
        return 0;
    }

    /**
     * 手机号登录
     * @param phoneNumber
     * @return 相应的返回码
     * 0:手机号正确
     * 1:手机号为空
     * 2:手机号格式不正确
     */
    public static int isInputLegal( String phoneNumber){
        if (!phoneNumber.isEmpty()){
            if (RegexUtils.isMobileExact(phoneNumber)){
                return 0;
            }else {
                ToastUtils.showLongToast("请输入正确的手机号");
                return 2;
            }
        }else {
            ToastUtils.showLongToast( "请输入手机号");
            return 1;
        }
    }

    /**
     * @param phoneNumber 用户输入的电话号码
     * @param defaultPhoneCode 系统返回的验证码
     * @param code 用户输入的验证码
     * @return 相应的返回码
     * -1:未知错误
     * 0:用户输入的验证码正确
     * 1:用户输入的电话号码为空
     * 2:用户输入的电话号码格式不正确
     * 3:用户输入的验证码为空
     * 4:系统没有返回验证码
     * 5:用户输入的验证码错误
     */
    public static int IsPhoneLoginLegal(String phoneNumber,String defaultPhoneCode,String code){
            switch (isInputLegal(phoneNumber)){
                case 0:
                    switch (checkPhoneCode(defaultPhoneCode,code)){
                        case 0:return 0;
                        case 1:return 3;
                        case 2:return 4;
                        case 3:return 5;
                        default: return -1;
                    }
                case 1:return 1;
                case 2:return 2;
                default:return -1;
            }
    }

    /**
     * @param defaultPhoneCode 后台返回的验证码
     * @param code 用户输入的验证码
     * @return 相应返回码
     * 0:用户输入的验证码正确
     * 1:用户没有输入验证码
     * 2:系统没有返回验证码
     * 3:用户输入的验证码不正确
     */
    public static int checkPhoneCode(String defaultPhoneCode,String code){
            if (!code.isEmpty()){
                if (!defaultPhoneCode.isEmpty()){
                    if (defaultPhoneCode.equals(code)){
                        return 0;
                    }else {
                        ToastUtils.showLongToast("请输入正确的验证码");
                        return 3;
                    }
                }else {
                    ToastUtils.showLongToast("系统错误");
                    return 2;
                }
            }else {
                ToastUtils.showLongToast("请输入验证码");
                return 1;
            }

    }
    /**
     * 账号密码
     * @param username
     * @param password
     * @return 相应的返回码
     * -1:未知错误
     * 0：用户名和密码本地验证成功
     * 1：用户名为空
     * 2: 用户名格式不正确
     * 3: 密码为空
     * 4: 密码格式不正确
     */
    public static int isInputLegal( String username,String password){
        switch (isUserNameLegal(username)){
            case 0:
                switch (isPasswordLegal(password)){
                    case 0:return 0;
                    case 1:return 3;
                    case 2:return 4;
                    default:return -1;
                }
            case 1: return 1;
            case 2: return 2;
            default:return -1;
        }

    }
    /**
     * @param context
     * @param phoneNumber
     * @param passWord
     * @return 相应的返回码
     * 0:本地验证通过
     * 1:用户输入的电话号码为空
     * 2:用户输入的电话号码格式不正确
     * 3: 密码为空
     * 4: 密码格式不正确
     */
    public static int checkPhonePassword(String phoneNumber,String passWord){
        switch (isInputLegal(phoneNumber)){
            case 0:
                switch (isPasswordLegal(passWord)){
                    case 0:return 0;
                    case 1:return 3;
                    case 2:return 4;
                    default: return -1;
                }
            case 1:return 1;
            case 2:return 2;
            default:return -1;
        }
    }
    /**
     * @param username
     * @return 相应的返回码
     * 0：用户名正确
     * 1：用户名为空
     * 2：用户名格式不正确
     * 3：
     */
    public static int isUserNameLegal(String username){
        if (!username.isEmpty()){
            if (RegexUtils.isUsername(username)){
                return 0;
            }else {
                ToastUtils.showLongToast("请输入正确的用户名");
                return 2;
            }
        }else {
            ToastUtils.showLongToast("请输入用户名");
            return 1;
        }
    }

    /**
     * @param password
     * @return resultCode:相应的返回码
     * 0：密码正确
     * 1：密码为空
     * 2：密码格式不正确
     */
    public static int isPasswordLegal(String password){

        String rex="^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){6,20}$";//以字母开头，长度在6~18之间，只能包含字符、数字和下划线（可根据业务进行调整）
        if (!password.isEmpty()){
            if (RegexUtils.isMatch(rex,password)){
                return 0;
            }
            ToastUtils.showLongToast("请输入符合格式的密码");
            return 2;
        }else {
            ToastUtils.showLongToast("请输入密码");
            return 1;
        }

    }

    /**
     * 取出token
     * @return token
     */
    public static String takeToken(){
        SharedPreferences sp = MyApplication.getInstance().getContext().getSharedPreferences("Login_token", Context.MODE_PRIVATE);
        String token = sp.getString("token", null);
        return token;
    }
    /**
     * 验证token
     * @return token
     */
    public static boolean checkLogin(){
        SharedPreferences sp = MyApplication.getInstance().getContext().getSharedPreferences("Login_token", Context.MODE_PRIVATE);
        String token = sp.getString("token", null);
        if (!(token==null)){
            return true;
        }else {
            return false;
        }

    }
    /**
     * @param token 登录后的token
     */
    public  static void saveToken(String token){
        SharedPreferences sp = MyApplication.getInstance().getContext().getSharedPreferences("Login_token", Context.MODE_PRIVATE);
        sp.edit().putString("token", token).commit();
    }
}
