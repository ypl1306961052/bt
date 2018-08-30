package com.yunyou.icloudinn.bookhouse.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.CustomProgress;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class DoorOpenActivity extends Activity {
private LinearLayout linearLayout_error,linearLayout_ok;
    private CustomProgress customProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customProgress=new CustomProgress(this,R.style.Custom_Progress,"加载中",false);
        setContentView(R.layout.activity_dooropen);
        initView();
        openDoor();

    }

    private void initView() {
        linearLayout_error=(LinearLayout)findViewById(R.id.door_error);
        linearLayout_ok=(LinearLayout)findViewById(R.id.door_OK);

    }


    private void openDoor() {
        customProgress.show();
        Intent intent=getIntent();
String wmj=intent.getStringExtra("wmjID");

        String url=Api.BASE_URL+"/api/Lock/verification?wmj="+wmj;
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showLongToast("网络错误" + e);
                        customProgress.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        customProgress.dismiss();
                       if(MyApplication.getInstance().isLogin()){
                           JSONObject jsonObject=null;
                           int statce= 0;
                           try {
                               jsonObject= new JSONObject(response);
                               statce = jsonObject.getInt("code");
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                           if(statce==100){

                               linearLayout_ok.setVisibility(View.VISIBLE);
                               linearLayout_error.setVisibility(View.GONE);

                           }else {
                               linearLayout_ok.setVisibility(View.GONE);
                               linearLayout_error.setVisibility(View.VISIBLE);
                           }
                       }else{
                           Intent intent1=new Intent(DoorOpenActivity.this,LoginActivity.class);
                           startActivity(intent1);
                       }

                    }
                });
    }
}
