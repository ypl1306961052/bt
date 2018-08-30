package com.yunyou.icloudinn.bookhouse.Util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.tencent.qalsdk.base.a.T;

/**
 * Created by Administrator on 2017/10/24.
 */

public class IMLoginUtils  {

    private static final int  ERR_IMSDK_KICKED_BY_OTHERS=6028;//IM聊天SDK在其他设备登录返回码
    /*
    * 获取用户签名，登录聊天
    * */
    public static void initConversation(Activity activity,UserData user,final Handler handlerMessage) {
        final int yunsu_id = user.getYunsu_id();
        OkHttpUtils.get() .url(Api.IM_USERSIG)
                .addParams("account", String.valueOf(yunsu_id))
                .build()
                .execute(new JsonCallback<JSONObject>(activity,true) {
                    @Override
                    public void onResponse(JSONObject data, int id) {
                        if (data.getString("imsig")==null){
                            return;
                        }
                        LogUtils.d("TIMLogin",data.getString("imsig").toString());
                        //聊天登录
                        TIMManager.getInstance().login(String.valueOf(yunsu_id), data.getString("imsig"), new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                LogUtils.d("TIMLogin","聊天未登录 "+ i + s);
                                if (i==ERR_IMSDK_KICKED_BY_OTHERS){
                                    ToastUtils.showShortToast("用户在其他设备登录");
                                }
                            }

                            @Override
                            public void onSuccess() {
                                LogUtils.d("TIMLogin","聊天登录成功 ");
                                Message message =  new Message();
                                message.what=2;
                                handlerMessage.sendMessage(message);
                            }
                        });
                    }
                });
    }
}
