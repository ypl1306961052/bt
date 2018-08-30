package com.yunyou.icloudinn.bookhouse.wxapi;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.umeng.weixin.callback.WXCallbackActivity;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.Config.MyPlatFormConfig;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

//import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果没回调onResp，八成是这句没有写
        MyApplication.mWxApi.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) ;
                else ToastUtils.showLongToast("登录失败");
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        LogUtils.i("wx登录"+code);
                        OkHttpUtils.post()
                                .url(Api.WX_LOGIN)
                                .addParams("appid", MyPlatFormConfig.WEIXIN_APPID)
                                .addParams("code",code)
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        ToastUtils.showLongToast("网络错误" + e);
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        try {
                                            JSONObject json = JSON.parseObject(response);
                                            if (json.getInteger("code") == 100) {
                                                UserData userData=JSON.parseObject(response,UserData.class);
                                                MyApplication.getInstance().saveUser(userData);
                                                ToastUtils.showLongToast("登录成功");
                                                finish();
                                            } else {
                                                ToastUtils.showLongToast( json.getString("msg"));
                                            }
                                        } catch (Exception e) {
                                            ToastUtils.showLongToast("网络错误" + e);

                                        }
                                    }
                                });
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求

                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        finish();
                        break;
                }
                break;
        }
    }
}


