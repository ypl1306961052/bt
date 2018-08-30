package com.yunyou.icloudinn.bookhouse;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;

import com.umeng.socialize.UMShareAPI;
import com.yunyou.icloudinn.bookhouse.Config.MyPlatFormConfig;
import com.yunyou.icloudinn.bookhouse.JavaBean.UserData;
import com.yunyou.icloudinn.bookhouse.Util.MyUtils;
import com.yunyou.icloudinn.bookhouse.Util.UserUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.jar.Manifest;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by lijunyu
 * on 2017/4/19
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    public static Context context;
    private UserData user;
    private Context myContext;
    public static IWXAPI mWxApi;
    private  TIMManager timManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MyUtils.init(this);
        user = UserUtils.getUser();
        initOkHttpClient();     // 网络框架
        initUM();               // 友盟
        initWXAPIFactory();     // 微信
        initWebView();          //x5内核初始化接口
        //initTIMManager();       //初始化IM管理
        myContext = getApplicationContext();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base); MultiDex.install(this);
    }

    //初始化IM聊天管理器
    private TIMManager getTIMManager() {
       TIMManager.getInstance().init(myContext,new TIMSdkConfig(1400011424));
        timManager =TIMManager.getInstance();
        return timManager;
    }

    private void initWXAPIFactory() {
        mWxApi = WXAPIFactory.createWXAPI(this, MyPlatFormConfig.WEIXIN_APPID, false);
        mWxApi.registerApp(MyPlatFormConfig.WEIXIN_APPID);
    }

    private void initUM() {
        UMShareAPI.get(this);
    }

    private void initWebView() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initOkHttpClient() {
        // 拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.i("access-token",getToken());
                Request request;
                if(getToken()==null||getToken()==""){
                    request= chain.request().newBuilder().build();
                }else {
                    request= chain.request()
                            .newBuilder()
                            .addHeader("access-token", getToken())
                            .build();
                }
                return chain.proceed(request);
            }

        };
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public Context getContext() {
        return myContext;
    }

    /**
     * 保存用户
     *
     * @param user
     */
    public void saveUser(UserData user) {
        this.user = user;
        UserUtils.saveUser(user);
    }

    public UserData getUser() {
        if (user == null) {
            user = UserUtils.getUser();
        }
        return user;
    }

    public String getToken() {
        if (user == null) {
            user = UserUtils.getUser();
        }
        if (user == null) {
            return "";
        } else {
            return user.getData().getAccess_token();
        }
    }

    /**
     * 退出登录,清空数据
     */
    public void logout() {
        saveUser(null);
    }

    public boolean isLogin() {
        return user == null ? false : true;
    }

    public static MyApplication getInstance() {
        return instance;
    }

}
