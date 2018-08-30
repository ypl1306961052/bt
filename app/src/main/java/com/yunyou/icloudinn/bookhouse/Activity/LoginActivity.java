package com.yunyou.icloudinn.bookhouse.Activity;


import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.yunyou.icloudinn.bookhouse.Callback.JsonCallback;
import com.yunyou.icloudinn.bookhouse.Config.Api;
import com.yunyou.icloudinn.bookhouse.Config.ResultCode;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.LoginUtil.CountDownTimer;
import com.yunyou.icloudinn.bookhouse.MyApplication;
import com.yunyou.icloudinn.bookhouse.R;
import com.yunyou.icloudinn.bookhouse.Ui.StatusBarCompat;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public  static int runningNum = 0;// 防止连续弹出两次,为了更快，请在调用的地方+1
    private EditText mInputPhone, mInputCode;
    private String mPhone, mCode;
    private Button mCheckLogin;
    private TextView mGetCode;
    private View mCheckCode;
    private boolean band = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.getInstance().logout();
        if (getIntent() != null) {
            band = getIntent().getBooleanExtra("bind", false);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarCompat.compat(this, getResources().getColor(R.color.green1));
        initView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(runningNum>0){
            runningNum = runningNum -1;
        }
    }

    private void initView() {
        mInputPhone = (EditText) findViewById(R.id.login_input_phone);
        mInputCode = (EditText) findViewById(R.id.login_input_code);
        mCheckLogin = (Button) findViewById(R.id.check_login);
        mGetCode = (TextView) findViewById(R.id.get_code);
        mCheckCode = findViewById(R.id.login_get_code);
        TextView mBind = (TextView) findViewById(R.id.login_title);
        View mBack = findViewById(R.id.back);
        View weixin = findViewById(R.id.weixin_login);
        weixin.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mBind.setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.title);
        TextView wx = (TextView) findViewById(R.id.wx_text);
        mCheckCode.setOnClickListener(this);
        mCheckLogin.setOnClickListener(this);
        if (band) {
            title.setText("绑定手机号");
            weixin.setVisibility(View.GONE);
            wx.setVisibility(View.GONE);
            mBind.setText("绑定");
        } else {
            title.setText("登录");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!band) {
            if (MyApplication.getInstance().isLogin()) {
                finish();
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_login:
                if (band) {
                    dobind();
                } else {
                    doLogin();
                }
                break;
            case R.id.login_get_code:
                getCode();
                break;
            case R.id.weixin_login:
                doWeixinLogin();
                break;
        }
    }

    private void doWeixinLogin() {
        if (!MyApplication.mWxApi.isWXAppInstalled()) {
            ToastUtils.showLongToast("您还未安装微信客户端");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        MyApplication.mWxApi.sendReq(req);
    }


    private void doLogin() {
        mCode = mInputCode.getText().toString();
        mPhone = mInputPhone.getText().toString();
        if (!mCode.isEmpty()) {
            OkHttpUtils.post()
                    .url(Api.CODE_LOGIN)
                    .addParams("phone", mPhone)
                    .addParams("sms_code", mCode)
                    .build()
                    .execute(new JsonCallback<UserData>(this,true) {
                        @Override
                        public void onResponse(UserData user, int id) {
                            MyApplication.getInstance().saveUser(user);
                            ToastUtils.showLongToast("登录成功");
                            setResult(ResultCode.LOGIN_SUCCEED);// 返回登录成功代码
                            finish();
                        }
                    });
        } else {
            ToastUtils.showLongToast("请输入验证码");
        }
    }

    private void dobind() {
        mCode = mInputCode.getText().toString();
        mPhone = mInputPhone.getText().toString();
        if (!mCode.isEmpty()) {
            OkHttpUtils
                    .post()
                    .url(Api.BIND)
                    .addParams("phone", mPhone)
                    .addParams("sms_code", mCode)
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
                                    MyApplication.getInstance().saveUser(JSON.parseObject(response, UserData.class));
                                    ToastUtils.showLongToast("绑定成功");
//                                    if (Frommain){
//                                        finish();
//                                    }else {
//                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                                    }
                                    finish();
                                } else {
                                    ToastUtils.showLongToast(json.getString("msg"));
                                }
                            } catch (Exception e) {
                                ToastUtils.showLongToast("网络错误" + e);

                            }
                        }
                    });
        } else {
            ToastUtils.showLongToast("请输入验证码");
        }
    }

    public void getCode() {
        mPhone = mInputPhone.getText().toString();
        OkHttpUtils.post().url(Api.SEND_CODE)
                .addParams("phone", mPhone)
                .addParams("action", band?"3":"1")// 0：短信登录;1：手机注册；2：修改密码；3：绑定手机；4：更换绑定手机
                .build()
                .execute(new JsonCallback<String>(this,true) {
                    @Override
                    public void onResponse(String data, int id) {
                        if(!band){
                            mCheckCode.setEnabled(false);
                            mCheckCode.setBackground(getResources().getDrawable(R.drawable.boder_of_login2));
                            Resources resource = getBaseContext().getResources();
                            ColorStateList csl = resource.getColorStateList(R.color.green1);
                            mGetCode.setTextColor(csl);
                            timer.start();
                        }
                        ToastUtils.showLongToast("已经发送验证码");
                    }
                });
    }


    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mGetCode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            Resources resource = getBaseContext().getResources();
            ColorStateList csl = resource.getColorStateList(R.color.white);
            mGetCode.setTextColor(csl);
            mGetCode.setText("发送验证码");
            mCheckCode.setBackground(getResources().getDrawable(R.drawable.booder_of_login));
            mCheckCode.setEnabled(true);
        }
    };

}

