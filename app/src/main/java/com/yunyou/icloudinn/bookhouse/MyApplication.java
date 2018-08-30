package com.yunyou.icloudinn.bookhouse;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
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
        initWebView();          // x5内核初始化接口
        initTIMManager();       // 初始化IM管理
        initImageLoader();      // 初始化网络图片加载框架
        myContext = getApplicationContext();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base); MultiDex.install(this);
    }

    private void initTIMManager() {
        //初始化IM的SDK
        TIMManager.getInstance().init(this,new TIMSdkConfig(1400011424).enableCrashReport(false));
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

    /**
     * w
     */
    private void initOkHttpClient() {
        // 拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request;
                if(getToken()==null||getToken()==""){
                    request= chain.request().newBuilder().build();
                }else {
                    Log.i("access-token",getToken());
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

    /**
     * 初始化图片加载缓存框架
     * 了避免图片过多造成OOM，那么这里加载图片的时候必不可少的需要做内存优化，图片的优化方式有很多，
     * 我这里采取了最简单最直接得方式，使用了开源的ImageLoader这个图片加载框架，
     * 减少了开发者一系列不必要而且时常会出现的麻烦，
     * 关于ImageLoader，GitHub地址是https://github.com/nostra13/Android-Universal-Image-Loader，
     */
    private void initImageLoader(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
                .showImageForEmptyUri(R.drawable.backgroung) //
                .showImageOnFail(R.drawable.backgroung) //
                .cacheInMemory(true) //
                .cacheOnDisk(true) //
                .build();//
        ImageLoaderConfiguration config = new ImageLoaderConfiguration//
                .Builder(getApplicationContext())//
                .defaultDisplayImageOptions(defaultOptions)//
                .discCacheSize(50 * 1024 * 1024)//
                .discCacheFileCount(100)// 缓存一百张图片
                .writeDebugLogs()//
                .build();//
        ImageLoader.getInstance().init(config);
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

    public void updateUser(UserData user) {
        this.user = user;
        UserUtils.updateUser(user);
    }

    public String getToken() {
        if (user == null) {
            user = UserUtils.getUser();
        }

        if (user == null) {
            return "";
        } else {
            return user.getAccess_token();
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
