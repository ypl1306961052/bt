package com.yunyou.icloudinn.bookhouse.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunyou.icloudinn.bookhouse.Callback.HttpCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

import okhttp3.Call;

public class HttpUtil {

    public static void okHttpPost( boolean isWithToken, HashMap params, String url, final HttpCallback httpCallback) {
        HashMap<String, String> head = new HashMap<>();
        OkHttpUtils
                .post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//
                        ToastUtils.showLongToast("网络错误" + e);
                        httpCallback.onFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject json = JSON.parseObject(response);
                            if (json.getInteger("code") == 100) {
                                httpCallback.onSuccess(json.getString("data"));
                            } else {
                                ToastUtils.showLongToast( json.getString("msg"));
                                httpCallback.onFail();
                            }
                        } catch (Exception e) {
                            ToastUtils.showLongToast("网络错误" + e);
                            httpCallback.onFail();
                        }

                    }
                });
    }
    public static void okHttpGet(boolean isWithToken,String url, final HttpCallback httpCallback){
        HashMap<String, String> head = new HashMap<>();
                OkHttpUtils
                        .get()
                        .url(url)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                ToastUtils.showLongToast("网络错误" + e);
                                httpCallback.onFail();
                            }

                            @Override
                            public void onResponse(String response, int id) {

                                try {
                                    JSONObject json = JSON.parseObject(response);
                                    LogUtils.i("你好"+json.getString("data"));
                                    if (json.getInteger("code") == 100) {
                                        httpCallback.onSuccess(json.getString("data"));
                                    } else {
                                        ToastUtils.showLongToast(json.getString("msg"));
                                    }
                                } catch (Exception e) {
                                    LogUtils.i("错误测试2"+e);
                                    ToastUtils.showLongToast("网络错误" + e);
                                    httpCallback.onFail();
                                }

                            }
                        });

    }
}

