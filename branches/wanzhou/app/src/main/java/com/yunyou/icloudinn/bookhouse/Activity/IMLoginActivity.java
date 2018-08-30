package com.yunyou.icloudinn.bookhouse.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.JavaBean.IMsigData;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Util.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

public class IMLoginActivity extends AppCompatActivity {

    private String UserSig;
    //private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imlogin);
        initData();
        initView();
    }

    private void initView() {

    }

    private void initData() {
        OkHttpUtils.post()
                .url(Api.IM_USERSIG)
                .addParams("account","admin")
                .addParams("user_id","23423")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                    IMsigData msigData = JSON.parseObject(response,IMsigData.class);
                        if (msigData.getCode()==100){
                            UserSig = msigData.getData().getImsig();
                            LogUtils.d("imsig",UserSig);
                        }
                    }
                });


    }
}
