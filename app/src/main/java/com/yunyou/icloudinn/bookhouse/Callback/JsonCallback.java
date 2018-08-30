package com.yunyou.icloudinn.bookhouse.Callback;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Looper;
import android.view.Window;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yunyou.icloudinn.bookhouse.Activity.LoginActivity;
import com.yunyou.icloudinn.bookhouse.Config.ResultCode;
import com.yunyou.icloudinn.bookhouse.JavaBean.ResultData;
import com.yunyou.icloudinn.bookhouse.Util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.security.auth.login.LoginException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * ================================================
 * 作    者：陈万洲
 * 版    本：1.0
 * 创建日期：2017/8/24
 * 描    述：默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 *           处理需要登录逻辑
 * 注    意：不能用其他类继承该类，否则无法使用
 * 原理参考：https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback
 * ================================================
 */

public abstract class JsonCallback<T> extends Callback<T> {

    private Activity activity;
    private ProgressDialog dialog; // 加载对话框
    private boolean isShowLoading = false; //是否显示对话框

    private void initDialog() {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    public JsonCallback(Activity activity,boolean isShowLoading) {
        this.activity = activity;
        this.isShowLoading = isShowLoading;
        if(isShowLoading){
            initDialog();
        }
    }

    @Override
    public void onBefore(Request request, int id) {
        if(isShowLoading) {
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    @Override
    public T parseNetworkResponse(Response response , int id) throws Exception
    {
        onAfter(id);
        ResultData<String> result = JSON.parseObject(response.body().string(),new TypeReference<ResultData<String>>(){});
        // 解析出当前类的父类上的泛型的真实泛型 ，继承该类是获取的泛型会变
        Type genType = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType)genType).getActualTypeArguments()[0];
        T data = null;

        // api接口状态
        if (result.getCode() == 100) {
            if(result.getData()==null||result.getData()=="")return null;
            if(type.equals(String.class)){
                data = (T)result.getData();
            }else {
                data = JSON.parseObject(result.getData(), type);
            }

        }else if(result.getCode() == 101||result.getCode() == 103){
            //根据取消其他请求 防止弹出多次登录界面
            OkHttpUtils.getInstance().cancelTag(activity);

            //有登录需求时 跳转登录界面
            Intent intent = new Intent();
            intent.setClass(activity, LoginActivity.class);

            if(!isTopLoginRun()&&LoginActivity.runningNum < 1){
                // 防止连续多次打开请登录界面，请在LoginActivity申请该静态变量，
                // 并在LoginActivity.java中的onStop（）方法中--1操作
                // 在此处 ++1操作（为了更快，如在LoginActivity的oncreate()中++1无法达到此效果）
                LoginActivity.runningNum = LoginActivity.runningNum + 1;
                activity.startActivityForResult(intent, ResultCode.LOGIN_SUCCEED);//请求码的值随便设置，但必须>=0
                throw new LoginException("没有登录哦");
            }

        } else {
            showToast(result.getMsg());
            throw new Exception("数据请求异常！");
        }
        if(data==null||data=="")throw new NullPointerException("数据为空！");
        return data;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

        if(e instanceof UnknownHostException||e instanceof ConnectException){
            ToastUtils.showLongToast("网络连接失败，请连接网络....");
        }else if(e instanceof SocketTimeoutException){
            ToastUtils.showLongToast("网络请求超时");
        }else if(e instanceof HttpRetryException){
            ToastUtils.showLongToast("服务器拒绝请求");
        }else if(e instanceof SocketException){
            //ToastUtils.showShortToast("网络请求取消！");
        }else if(e instanceof LoginException){
            ToastUtils.showShortToast(e.getMessage());
        } else {
            //ToastUtils.showShortToast("网络错误！"+e);
        }

    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        if(!isShowLoading)return;
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            }catch (Exception e){

            }

        }
    }

    // 子线程弹出toast
    private void showToast(String s){
        Looper.prepare();
        ToastUtils.showLongToast(s);
        Looper.loop();
    }

    // 判断LoginActivity是否处于顶层
    private boolean isTopLoginRun()
    {
        ActivityManager am = (ActivityManager)activity.getSystemService(ACTIVITY_SERVICE);
        String topActivity = am.getRunningTasks(1).get(0).topActivity.getClassName();
        if(topActivity==LoginActivity.class.toString()){
            return true;
        }else {
            return false;
        }
    }

}
