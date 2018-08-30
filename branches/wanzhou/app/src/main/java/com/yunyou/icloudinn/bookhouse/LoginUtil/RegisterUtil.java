package com.yunyou.icloudinn.bookhouse.LoginUtil;

import android.content.Context;
import android.widget.Toast;

import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;

/**
 * Created by lijunyu
 * on 2017/3/27
 */
public class RegisterUtil  {
    /**
     * @param password 用户注册所输入的密码
     * @param affirmPassword 用户重复输入的密码
     * @return 返回码
     * 0:正确
     * 1:密码与重复密码不符
     */
    public static int affirmPassword(String password,String affirmPassword){
        if (password.equals(affirmPassword)){
            return 0;
        }else {
            ToastUtils.showLongToast("重复密码不正确");
            return 1;
        }

    }


    /**
     * @param email  用户输入的email
     * @return 相应的返回码
     * 0:成功
     * 1:不是email
     */
    public static int checkEail(String email){
        if (RegexUtils.isEmail(email)){
            return 0;
        }
        return 1;
    }
    /**
     * @param code  用户输入的验证码
     * @param defaltCode  用户输入的验证码
     * @param passWord  用户输入的验证码
     * @param rePassWord  用户输入的验证码
     * @return 相应的返回码
     * -1:未知错误
     * 0:成功
     * 1:用户没有输入验证码
     * 2.系统没有返回验证码
     * 3.验证码不符
     * 4.密码不符
     */
    public static int checkPhoneCodeRes(String defaltCode , String code , String passWord, String rePassWord){
        switch (LoginUtil.checkPhoneCode(defaltCode,code)){
            case 0:
                switch (affirmPassword(passWord,rePassWord)){
                    case 0:
                        return 0;
                    case 1:
                        return 4;
                }
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return -1;
        }

    }
}
